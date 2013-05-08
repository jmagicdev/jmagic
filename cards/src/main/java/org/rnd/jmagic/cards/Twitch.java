package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Twitch")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Twitch extends Card
{
	public Twitch(GameState state)
	{
		super(state);

		Target target = this.addTarget(Union.instance(CreaturePermanents.instance(), Union.instance(LandPermanents.instance(), ArtifactPermanents.instance())), "target artifact, creature, or land");

		this.addEffect(youMay(tapOrUntap(targetedBy(target), "target artifact, creature, or land"), "You may tap or untap target artifact, creature, or land."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
