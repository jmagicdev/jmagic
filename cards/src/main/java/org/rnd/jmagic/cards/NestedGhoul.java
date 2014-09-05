package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Nested Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.WARRIOR})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class NestedGhoul extends Card
{
	public static final class NestedGhoulAbility0 extends EventTriggeredAbility
	{
		public NestedGhoulAbility0(GameState state)
		{
			super(state, "Whenever a source deals damage to Nested Ghoul, put a 2/2 black Zombie creature token onto the battlefield.");
			this.addPattern(whenIsDealtDamage(ABILITY_SOURCE_OF_THIS));

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			factory.setColors(Color.BLACK);
			factory.setSubTypes(SubType.ZOMBIE);
			this.addEffect(factory.getEventFactory());
		}
	}

	public NestedGhoul(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Whenever a source deals damage to Nested Ghoul, put a 2/2 black
		// Zombie creature token onto the battlefield.
		this.addAbility(new NestedGhoulAbility0(state));
	}
}
