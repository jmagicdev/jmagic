package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Springleaf Drum")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SpringleafDrum extends Card
{
	public static final class SpringleafDrumAbility0 extends ActivatedAbility
	{
		public SpringleafDrumAbility0(GameState state)
		{
			super(state, "(T), Tap an untapped creature you control: Add one mana of any color to your mana pool.");
			this.costsTap = true;

			SetGenerator yourUntappedCreatures = Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL);

			EventFactory tapACreature = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped creature you control");
			tapACreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapACreature.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapACreature.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			tapACreature.parameters.put(EventType.Parameter.CHOICE, yourUntappedCreatures);
			this.addCost(tapACreature);

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}
	}

	public SpringleafDrum(GameState state)
	{
		super(state);

		// (T), Tap an untapped creature you control: Add one mana of any color
		// to your mana pool.
		this.addAbility(new SpringleafDrumAbility0(state));
	}
}
