package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mercurial Pretender")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class MercurialPretender extends Card
{
	public static final class MercurialPretenderAbility0 extends ActivatedAbility
	{
		public MercurialPretenderAbility0(GameState state)
		{
			super(state, "(2)(U)(U): Return this creature to its owner's hand.");
			this.setManaCost(new ManaPool("(2)(U)(U)"));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return this creature to its owner's hand."));
		}
	}

	public MercurialPretender(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// You may have Mercurial Pretender enter the battlefield as a copy of
		// any creature you control except it gains
		// "(2)(U)(U): Return this creature to its owner's hand."
		org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy pretend = new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(CREATURES_YOU_CONTROL);
		this.addAbility(pretend.setAbility(MercurialPretenderAbility0.class).setName("You may have Mercurial Pretender enter the battlefield as a copy of any creature you control except it gains \"(2)(U)(U): Return this creature to its owner's hand.\"").getStaticAbility(state));
	}
}
