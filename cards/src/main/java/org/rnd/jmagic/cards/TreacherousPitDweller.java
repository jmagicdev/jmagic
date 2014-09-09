package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Treacherous Pit-Dweller")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("BB")
@ColorIdentity({Color.BLACK})
public final class TreacherousPitDweller extends Card
{
	public static final class TreacherousPitDwellerAbility0 extends EventTriggeredAbility
	{
		public TreacherousPitDwellerAbility0(GameState state)
		{
			super(state, "When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.");
			this.addPattern(new SimpleZoneChangePattern(GraveyardOf.instance(Players.instance()), Battlefield.instance(), ABILITY_SOURCE_OF_THIS, false));

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, target);
			this.addEffect(createFloatingEffect(Empty.instance(), "Target opponent gains control of it.", part));
		}
	}

	public TreacherousPitDweller(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// When Treacherous Pit-Dweller enters the battlefield from a graveyard,
		// target opponent gains control of it.
		this.addAbility(new TreacherousPitDwellerAbility0(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
