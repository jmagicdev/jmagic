package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Renowned Weaver")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class RenownedWeaver extends Card
{
	public static final class RenownedWeaverAbility0 extends ActivatedAbility
	{
		public RenownedWeaverAbility0(GameState state)
		{
			super(state, "(1)(G), Sacrifice Renowned Weaver: Put a 1/3 green Spider enchantment creature token with reach onto the battlefield.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.addCost(sacrificeThis("Renowned Weaver"));

			CreateTokensFactory spider = new CreateTokensFactory(1, 1, 3, "Put a 1/3 green Spider enchantment creature token with reach onto the battlefield.");
			spider.setColors(Color.GREEN);
			spider.setSubTypes(SubType.SPIDER);
			spider.setEnchantment();
			spider.addAbility(org.rnd.jmagic.abilities.keywords.Reach.class);
			this.addEffect(spider.getEventFactory());
		}
	}

	public RenownedWeaver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(G), Sacrifice Renowned Weaver: Put a 1/3 green Spider enchantment
		// creature token with reach onto the battlefield. (It can block
		// creatures with flying.)
		this.addAbility(new RenownedWeaverAbility0(state));
	}
}
