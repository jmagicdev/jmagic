package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nicol Bolas, Planeswalker")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.BOLAS})
@ManaCost("4UBBR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class NicolBolasPlaneswalker extends Card
{
	public static final class BeatEm extends LoyaltyAbility
	{
		public BeatEm(GameState state)
		{
			super(state, +3, "Destroy target noncreature permanent.");

			Target target = this.addTarget(RelativeComplement.instance(Permanents.instance(), CreaturePermanents.instance()), "target noncreature permanent");

			this.addEffect(destroy(targetedBy(target), "Destroy target noncreature permanent."));
		}
	}

	public static final class JoinEm extends LoyaltyAbility
	{
		public JoinEm(GameState state)
		{
			super(state, -2, "Gain control of target creature.");

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			this.addEffect(createFloatingEffect(Empty.instance(), "Gain control of target creature.", controlPart));
		}
	}

	public static final class OrJustWin extends LoyaltyAbility
	{
		public OrJustWin(GameState state)
		{
			super(state, -9, "Nicol Bolas, Planeswalker deals 7 damage to target player. That player discards seven cards, then sacrifices seven permanents.");

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(permanentDealDamage(7, targetedBy(target), "Nicol Bolas, Planeswalker deals 7 damage to target player."));

			this.addEffect(discardCards(targetedBy(target), 7, "That player discards seven cards,"));

			this.addEffect(sacrifice(targetedBy(target), 7, Permanents.instance(), "then sacrifices seven permanents."));
		}
	}

	public NicolBolasPlaneswalker(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(5);

		this.addAbility(new BeatEm(state));

		this.addAbility(new JoinEm(state));

		this.addAbility(new OrJustWin(state));
	}
}
