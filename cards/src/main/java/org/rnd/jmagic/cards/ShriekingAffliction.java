package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shrieking Affliction")
@Types({Type.ENCHANTMENT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class ShriekingAffliction extends Card
{
	public static final class ShriekingAfflictionAbility0 extends EventTriggeredAbility
	{
		public ShriekingAfflictionAbility0(GameState state)
		{
			super(state, "At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, he or she loses 3 life.");
			this.addPattern(atTheBeginningOfEachOpponentsUpkeeps());

			SetGenerator upkeep = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP);
			SetGenerator thatPlayer = OwnerOf.instance(upkeep);
			SetGenerator hasOneOrFewer = Intersect.instance(Between.instance(0, 1), Count.instance(InZone.instance(HandOf.instance(thatPlayer))));
			this.interveningIf = hasOneOrFewer;

			this.addEffect(loseLife(thatPlayer, 3, "He or she loses 3 life."));
		}
	}

	public ShriekingAffliction(GameState state)
	{
		super(state);

		// At the beginning of each opponent's upkeep, if that player has one or
		// fewer cards in hand, he or she loses 3 life.
		this.addAbility(new ShriekingAfflictionAbility0(state));
	}
}
