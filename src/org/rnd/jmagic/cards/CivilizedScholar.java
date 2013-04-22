package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Civilized Scholar")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ADVISOR})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
@BackFace(HomicidalBrute.class)
public final class CivilizedScholar extends Card
{
	public static final class CivilizedScholarAbility0 extends ActivatedAbility
	{
		public CivilizedScholarAbility0(GameState state)
		{
			super(state, "(T): Draw a card, then discard a card. If a creature card is discarded this way, untap Civilized Scholar, then transform it.");
			this.costsTap = true;

			this.addEffect(drawACard());

			EventFactory discard = discardCards(You.instance(), 1, "Discard a card.");
			this.addEffect(discard);

			SetGenerator creatureCardDiscarded = Intersect.instance(NewObjectOf.instance(EffectResult.instance(discard)), HasType.instance(Type.CREATURE));
			EventFactory untap = untap(ABILITY_SOURCE_OF_THIS, "Untap Civilized Scholar.");
			EventFactory transform = transformThis("Civilized Scholar");
			this.addEffect(ifThen(creatureCardDiscarded, sequence(untap, transform), "If a creature card is discarded this way, untap Civilized Scholar, then transform it."));
		}
	}

	public CivilizedScholar(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (T): Draw a card, then discard a card. If a creature card is
		// discarded this way, untap Civilized Scholar, then transform it.
		this.addAbility(new CivilizedScholarAbility0(state));
	}
}
