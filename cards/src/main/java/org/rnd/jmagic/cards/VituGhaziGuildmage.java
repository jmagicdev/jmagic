package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vitu-Ghazi Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.DRYAD, SubType.SHAMAN})
@ManaCost("GW")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class VituGhaziGuildmage extends Card
{
	public static final class VituGhaziGuildmageAbility0 extends ActivatedAbility
	{
		public VituGhaziGuildmageAbility0(GameState state)
		{
			super(state, "(4)(G)(W): Put a 3/3 green Centaur creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(4)(G)(W)"));

			// Put a 3/3 green Centaur creature token onto the battlefield.
			CreateTokensFactory factory = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Centaur creature token onto the battlefield.");
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.CENTAUR);
			this.addEffect(factory.getEventFactory());
		}
	}

	public static final class VituGhaziGuildmageAbility1 extends ActivatedAbility
	{
		public VituGhaziGuildmageAbility1(GameState state)
		{
			super(state, "(2)(G)(W): Populate.");
			this.setManaCost(new ManaPool("(2)(G)(W)"));
			this.addEffect(populate());
		}
	}

	public VituGhaziGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (4)(G)(W): Put a 3/3 green Centaur creature token onto the
		// battlefield.
		this.addAbility(new VituGhaziGuildmageAbility0(state));

		// (2)(G)(W): Populate. (Put a token onto the battlefield that's a copy
		// of a creature token you control.)
		this.addAbility(new VituGhaziGuildmageAbility1(state));
	}
}
