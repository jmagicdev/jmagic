package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mercurial Chemister")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("3UR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class MercurialChemister extends Card
{
	public static final class MercurialChemisterAbility0 extends ActivatedAbility
	{
		public MercurialChemisterAbility0(GameState state)
		{
			super(state, "(U), (T): Draw two cards.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;
			this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
		}
	}

	public static final class MercurialChemisterAbility1 extends ActivatedAbility
	{
		public MercurialChemisterAbility1(GameState state)
		{
			super(state, "(R), (T), Discard a card: Mercurial Chemister deals damage to target creature equal to the discarded card's converted mana cost.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;

			EventFactory discard = discardCards(You.instance(), 1, "Discard a card");
			this.addCost(discard);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(ConvertedManaCostOf.instance(NewObjectOf.instance(CostResult.instance(discard))), target, "Mercurial Chemister deals damage to target creature equal to the discarded card's converted mana cost."));
		}
	}

	public MercurialChemister(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (U), (T): Draw two cards.
		this.addAbility(new MercurialChemisterAbility0(state));

		// (R), (T), Discard a card: Mercurial Chemister deals damage to target
		// creature equal to the discarded card's converted mana cost.
		this.addAbility(new MercurialChemisterAbility1(state));
	}
}
