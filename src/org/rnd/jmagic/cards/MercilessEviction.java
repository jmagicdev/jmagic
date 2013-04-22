package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merciless Eviction")
@Types({Type.SORCERY})
@ManaCost("4WB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class MercilessEviction extends Card
{
	public MercilessEviction(GameState state)
	{
		super(state);

		// Choose one \u2014

		// Exile all artifacts
		this.addEffect(1, exile(ArtifactPermanents.instance(), "Exile all artifacts"));

		// exile all creatures
		this.addEffect(2, exile(CreaturePermanents.instance(), "exile all creatures"));

		// exile all enchantments
		this.addEffect(3, exile(EnchantmentPermanents.instance(), "exile all enchantments"));

		// exile all planeswalkers.
		this.addEffect(4, exile(Intersect.instance(HasType.instance(Type.PLANESWALKER), Permanents.instance()), "exile all planeswalkers."));
	}
}
