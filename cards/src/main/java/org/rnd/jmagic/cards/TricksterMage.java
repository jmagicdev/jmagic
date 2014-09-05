package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Trickster Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SPELLSHAPER})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Nemesis.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TricksterMage extends Card
{
	public static final class TricksterMageAbility0 extends ActivatedAbility
	{
		public TricksterMageAbility0(GameState state)
		{
			super(state, "(U), (T), Discard a card: You may tap or untap target artifact, creature, or land.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			Target target = this.addTarget(Union.instance(CreaturePermanents.instance(), LandPermanents.instance(), ArtifactPermanents.instance()), "target artifact, creature, or land");

			this.addEffect(youMay(tapOrUntap(targetedBy(target), "target artifact, creature, or land"), "You may tap or untap target artifact, creature, or land."));
		}
	}

	public TricksterMage(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (U), (T), Discard a card: You may tap or untap target artifact,
		// creature, or land.
		this.addAbility(new TricksterMageAbility0(state));
	}
}
