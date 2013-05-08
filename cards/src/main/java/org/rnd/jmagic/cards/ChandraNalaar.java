package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chandra Nalaar")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.CHANDRA})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ChandraNalaar extends Card
{
	public static final class Ping extends LoyaltyAbility
	{
		public Ping(GameState state)
		{
			super(state, +1, "Chandra Nalaar deals 1 damage to target player.");
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Chandra Nalaar deals 1 damage to target player."));
		}
	}

	public static final class Smack extends LoyaltyAbility
	{
		public Smack(GameState state)
		{
			super(state, LoyaltyAbility.OtherCost.MINUS_X, "Chandra Nalaar deals X damage to target creature.");
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(permanentDealDamage(ValueOfX.instance(This.instance()), targetedBy(target), "Chandra Nalaar deals X damage to target creature."));
		}
	}

	public static final class Boom extends LoyaltyAbility
	{
		public Boom(GameState state)
		{
			super(state, -8, "Chandra Nalaar deals 10 damage to target player and each creature he or she controls.");
			Target target = this.addTarget(Players.instance(), "target player");

			SetGenerator targetPlayer = targetedBy(target);
			SetGenerator hisCreatures = Intersect.instance(ControlledBy.instance(targetPlayer), HasType.instance(Type.CREATURE));
			this.addEffect(permanentDealDamage(10, Union.instance(targetPlayer, hisCreatures), "Chandra Nalaar deals 10 damage to target player and each creature he or she controls."));
		}
	}

	public ChandraNalaar(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(6);

		this.addAbility(new Ping(state));
		this.addAbility(new Smack(state));
		this.addAbility(new Boom(state));
	}
}
