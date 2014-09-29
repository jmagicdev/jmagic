package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Savage Knuckleblade")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.WARRIOR})
@ManaCost("GUR")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class SavageKnuckleblade extends Card
{
	public static final class SavageKnucklebladeAbility0 extends ActivatedAbility
	{
		public SavageKnucklebladeAbility0(GameState state)
		{
			super(state, "(2)(G): Savage Knuckleblade gets +2/+2 until end of turn. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(2)(G)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Savage Knuckleblade gets +2/+2 until end of turn."));
			this.perTurnLimit(1);
		}
	}

	public static final class SavageKnucklebladeAbility1 extends ActivatedAbility
	{
		public SavageKnucklebladeAbility1(GameState state)
		{
			super(state, "(2)(U): Return Savage Knuckleblade to its owner's hand.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Savage Knuckleblade to its owner's hand."));
		}
	}

	public static final class SavageKnucklebladeAbility2 extends ActivatedAbility
	{
		public SavageKnucklebladeAbility2(GameState state)
		{
			super(state, "(R): Savage Knuckleblade gains haste until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Haste.class, "Savage Knuckleblade gains haste until end of turn."));
		}
	}

	public SavageKnuckleblade(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// (2)(G): Savage Knuckleblade gets +2/+2 until end of turn. Activate
		// this ability only once each turn.
		this.addAbility(new SavageKnucklebladeAbility0(state));

		// (2)(U): Return Savage Knuckleblade to its owner's hand.
		this.addAbility(new SavageKnucklebladeAbility1(state));

		// (R): Savage Knuckleblade gains haste until end of turn.
		this.addAbility(new SavageKnucklebladeAbility2(state));
	}
}
