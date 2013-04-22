package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skarrg Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("RG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class SkarrgGuildmage extends Card
{
	public static final class SkarrgGuildmageAbility0 extends ActivatedAbility
	{
		public SkarrgGuildmageAbility0(GameState state)
		{
			super(state, "(R)(G): Creatures you control gain trample until end of turn.");
			this.setManaCost(new ManaPool("(R)(G)"));

			this.addEffect(addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Trample.class, "Creatures you control gain trample until end of turn."));
		}
	}

	public static final class SkarrgGuildmageAbility1 extends ActivatedAbility
	{
		public SkarrgGuildmageAbility1(GameState state)
		{
			super(state, "(1)(R)(G): Target land you control becomes a 4/4 Elemental creature until end of turn. It's still a land.");
			this.setManaCost(new ManaPool("(1)(R)(G)"));

			SetGenerator landYouControl = Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()));
			SetGenerator target = targetedBy(this.addTarget(landYouControl, "target land"));
			Animator animate = new Animator(target, 4, 4);
			this.addEffect(createFloatingEffect("Target land you control becomes a 4/4 Elemental creature until end of turn. It's still a land.", animate.getParts()));
		}
	}

	public SkarrgGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R)(G): Creatures you control gain trample until end of turn.
		this.addAbility(new SkarrgGuildmageAbility0(state));

		// (1)(R)(G): Target land you control becomes a 4/4 Elemental creature
		// until end of turn. It's still a land.
		this.addAbility(new SkarrgGuildmageAbility1(state));
	}
}
