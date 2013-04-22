package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Sunhome Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("RW")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class SunhomeGuildmage extends Card
{
	public static final class SunhomeGuildmageAbility0 extends ActivatedAbility
	{
		public SunhomeGuildmageAbility0(GameState state)
		{
			super(state, "(1)(R)(W): Creatures you control get +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(1)(R)(W)"));
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +0, "Creatures you control get +1/+0 until end of turn."));
		}
	}

	public static final class SunhomeGuildmageAbility1 extends ActivatedAbility
	{
		public SunhomeGuildmageAbility1(GameState state)
		{
			super(state, "(2)(R)(W): Put a 1/1 red and white Soldier creature token with haste onto the battlefield.");
			this.setManaCost(new ManaPool("(2)(R)(W)"));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red and white Soldier creature token with haste onto the battlefield.");
			token.setColors(Color.RED, Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public SunhomeGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(R)(W): Creatures you control get +1/+0 until end of turn.
		this.addAbility(new SunhomeGuildmageAbility0(state));

		// (2)(R)(W): Put a 1/1 red and white Soldier creature token with haste
		// onto the battlefield.
		this.addAbility(new SunhomeGuildmageAbility1(state));
	}
}
