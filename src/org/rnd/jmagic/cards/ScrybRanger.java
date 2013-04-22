package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scryb Ranger")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ScrybRanger extends Card
{
	public static final class ScrybRangerAbility2 extends ActivatedAbility
	{
		public ScrybRangerAbility2(GameState state)
		{
			super(state, "Return a Forest you control to its owner's hand: Untap target creature. Activate this ability only once each turn.");

			EventFactory bounce = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return a Forest you control to its owner's hand");
			bounce.parameters.put(EventType.Parameter.CAUSE, This.instance());
			bounce.parameters.put(EventType.Parameter.PLAYER, You.instance());
			bounce.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounce.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasSubType.instance(SubType.FOREST), ControlledBy.instance(You.instance())));
			this.addCost(bounce);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			this.addEffect(untap(target, "Untap target creature."));

			this.perTurnLimit(1);
		}
	}

	public ScrybRanger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Protection from blue
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlue(state));

		// Return a Forest you control to its owner's hand: Untap target
		// creature. Activate this ability only once each turn.
		this.addAbility(new ScrybRangerAbility2(state));
	}
}
