package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scrapyard Salvo")
@Types({Type.SORCERY})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ScrapyardSalvo extends Card
{
	public ScrapyardSalvo(GameState state)
	{
		super(state);

		// Scrapyard Salvo deals damage to target player equal to the number of
		// artifact cards in your graveyard.
		SetGenerator artifacts = HasType.instance(Type.ARTIFACT);
		SetGenerator inYourYard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator amount = Count.instance(Intersect.instance(artifacts, inYourYard));
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(spellDealDamage(amount, target, "Scrapyard Salvo deals damage to target player equal to the number of artifact cards in your graveyard."));
	}
}
