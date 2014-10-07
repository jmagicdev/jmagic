package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Font of Return")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class FontofReturn extends Card
{
	public static final class FontofReturnAbility0 extends ActivatedAbility
	{
		public FontofReturnAbility0(GameState state)
		{
			super(state, "(3)(B), Sacrifice Font of Return: Return up to three target creature cards from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(3)(B)"));
			this.addCost(sacrificeThis("Font of Return"));

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "up to three target creature cards from your graveyard").setNumber(0, 3));

			this.addEffect(putIntoHand(target, You.instance(), "Return up to three target creature cards from your graveyard to your hand."));
		}
	}

	public FontofReturn(GameState state)
	{
		super(state);

		// (3)(B), Sacrifice Font of Return: Return up to three target creature
		// cards from your graveyard to your hand.
		this.addAbility(new FontofReturnAbility0(state));
	}
}
