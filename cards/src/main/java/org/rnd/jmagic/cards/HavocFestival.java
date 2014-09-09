package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Havoc Festival")
@Types({Type.ENCHANTMENT})
@ManaCost("4BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class HavocFestival extends Card
{
	public static final class HavocFestivalAbility0 extends StaticAbility
	{
		public HavocFestivalAbility0(GameState state)
		{
			super(state, "Players can't gain life.");

			SimpleEventPattern gainPattern = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(gainPattern));
			this.addEffectPart(part);
		}
	}

	public static final class HavocFestivalAbility1 extends EventTriggeredAbility
	{
		public HavocFestivalAbility1(GameState state)
		{
			super(state, "At the beginning of each player's upkeep, that player loses half his or her life, rounded up.");
			this.addPattern(atTheBeginningOfEachPlayersUpkeep());

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));
			this.addEffect(loseLife(thatPlayer, DivideBy.instance(LifeTotalOf.instance(thatPlayer), numberGenerator(2), true), "That player loses half his or her life, rounded up."));
		}
	}

	public HavocFestival(GameState state)
	{
		super(state);

		// Players can't gain life.
		this.addAbility(new HavocFestivalAbility0(state));

		// At the beginning of each player's upkeep, that player loses half his
		// or her life, rounded up.
		this.addAbility(new HavocFestivalAbility1(state));
	}
}
