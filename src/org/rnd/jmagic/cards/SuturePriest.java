package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Suture Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SuturePriest extends Card
{
	public static final class SuturePriestAbility0 extends EventTriggeredAbility
	{
		public SuturePriestAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, you may gain 1 life.");
			SetGenerator yourOtherCreatures = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), yourOtherCreatures, false));

			this.addEffect(youMay(gainLife(You.instance(), 1, "You gain 1 life"), "You may gain 1 life."));
		}
	}

	public static final class SuturePriestAbility1 extends EventTriggeredAbility
	{
		public SuturePriestAbility1(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under an opponent's control, you may have that player lose 1 life.");
			SetGenerator othersCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), othersCreatures, false));

			SetGenerator thatPlayer = ControllerOf.instance(TriggerZoneChange.instance(This.instance()));
			this.addEffect(youMay(loseLife(thatPlayer, 1, "That player loses 1 life"), "You may have that player lose 1 life."));
		}
	}

	public SuturePriest(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever another creature enters the battlefield under your control,
		// you may gain 1 life.
		this.addAbility(new SuturePriestAbility0(state));

		// Whenever a creature enters the battlefield under an opponent's
		// control, you may have that player lose 1 life.
		this.addAbility(new SuturePriestAbility1(state));
	}
}
