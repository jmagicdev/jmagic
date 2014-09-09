package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pernicious Deed")
@Types({Type.ENCHANTMENT})
@ManaCost("1BG")
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class PerniciousDeed extends Card
{
	public static final class PerniciousDeedAbility0 extends ActivatedAbility
	{
		public PerniciousDeedAbility0(GameState state)
		{
			super(state, "(X), Sacrifice Pernicious Deed: Destroy each artifact, creature, and enchantment with converted mana cost X or less.");
			this.setManaCost(new ManaPool("(X)"));
			this.addCost(sacrificeThis("Pernicious Deed"));

			SetGenerator stuff = Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), EnchantmentPermanents.instance());
			SetGenerator X = ValueOfX.instance(This.instance());
			SetGenerator toDestroy = Intersect.instance(stuff, HasConvertedManaCost.instance(Between.instance(numberGenerator(0), X)));
			this.addEffect(destroy(toDestroy, "Destroy each artifact, creature, and enchantment with converted mana cost X or less."));
		}
	}

	public PerniciousDeed(GameState state)
	{
		super(state);

		// (X), Sacrifice Pernicious Deed: Destroy each artifact, creature, and
		// enchantment with converted mana cost X or less.
		this.addAbility(new PerniciousDeedAbility0(state));
	}
}
