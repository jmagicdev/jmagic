package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Evanescent Intellect")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class EvanescentIntellect extends Card
{
	public static final class MillStuff extends ActivatedAbility
	{
		public MillStuff(GameState state)
		{
			super(state, "(1)(U), (T): Target player puts the top three cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(1)(U)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 3, "Target player puts the top three cards of his or her library into his or her graveyard."));
		}
	}

	public static final class EvanescentIntellectAbility1 extends StaticAbility
	{
		public EvanescentIntellectAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(1)(U), (T): Target player puts the top three cards of his or her library into his or her graveyard.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), MillStuff.class));
		}
	}

	public EvanescentIntellect(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "(1)(U), (T): Target player puts the top three cards of his or her library into his or her graveyard."
		this.addAbility(new EvanescentIntellectAbility1(state));
	}
}
