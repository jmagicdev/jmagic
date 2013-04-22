package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Borborygmos Enraged")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.CYCLOPS})
@ManaCost("4RRGG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class BorborygmosEnraged extends Card
{
	public static final class BorborygmosEnragedAbility1 extends EventTriggeredAbility
	{
		public BorborygmosEnragedAbility1(GameState state)
		{
			super(state, "Whenever Borborygmos Enraged deals combat damage to a player, reveal the top three cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			EventFactory reveal = reveal(TopCards.instance(3, LibraryOf.instance(You.instance())), "Reveal the top three cards of your library.");
			this.addEffect(reveal);

			SetGenerator revealed = EffectResult.instance(reveal);

			this.addEffect(bounce(Intersect.instance(revealed, HasType.instance(Type.LAND)), "Put all land cards revealed this way into your hand"));
			this.addEffect(putIntoGraveyard(revealed, "and the rest into your graveyard."));
		}
	}

	public static final class BorborygmosEnragedAbility2 extends ActivatedAbility
	{
		public BorborygmosEnragedAbility2(GameState state)
		{
			super(state, "Discard a land card: Borborygmos Enraged deals 3 damage to target creature or player.");

			this.addCost(discardCards(You.instance(), 1, HasType.instance(Type.LAND), "Discard a land card"));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(3, target, "Borborygmos Enraged deals 3 damage to target creature or player."));
		}
	}

	public BorborygmosEnraged(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(6);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Borborygmos Enraged deals combat damage to a player, reveal
		// the top three cards of your library. Put all land cards revealed this
		// way into your hand and the rest into your graveyard.
		this.addAbility(new BorborygmosEnragedAbility1(state));

		// Discard a land card: Borborygmos Enraged deals 3 damage to target
		// creature or player.
		this.addAbility(new BorborygmosEnragedAbility2(state));
	}
}
