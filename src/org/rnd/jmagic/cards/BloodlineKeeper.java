package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodline Keeper")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
@BackFace(LordofLineage.class)
public final class BloodlineKeeper extends Card
{
	public static final class BloodlineKeeperAbility1 extends ActivatedAbility
	{
		public BloodlineKeeperAbility1(GameState state)
		{
			super(state, "(T): Put a 2/2 black Vampire creature token with flying onto the battlefield.");
			this.costsTap = true;

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Vampire creature token with flying onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.VAMPIRE);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class BloodlineKeeperAbility2 extends ActivatedAbility
	{
		public BloodlineKeeperAbility2(GameState state)
		{
			super(state, "(B): Transform Bloodline Keeper. Activate this ability only if you control five or more Vampires.");
			this.setManaCost(new ManaPool("(B)"));

			this.addEffect(transformThis("Bloodline Keeper"));

			SetGenerator vampires = Intersect.instance(HasSubType.instance(SubType.VAMPIRE), ControlledBy.instance(You.instance()));
			SetGenerator lessThanFiveVampires = Intersect.instance(Count.instance(vampires), Between.instance(null, 4));
			this.addActivateRestriction(lessThanFiveVampires);
		}
	}

	public BloodlineKeeper(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (T): Put a 2/2 black Vampire creature token with flying onto the
		// battlefield.
		this.addAbility(new BloodlineKeeperAbility1(state));

		// (B): Transform Bloodline Keeper. Activate this ability only if you
		// control five or more Vampires.
		this.addAbility(new BloodlineKeeperAbility2(state));
	}
}
