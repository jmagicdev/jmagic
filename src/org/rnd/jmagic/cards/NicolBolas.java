package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nicol Bolas")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON, SubType.ELDER})
@ManaCost("2UUBBRR")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class NicolBolas extends Card
{
	public static final class NicolBolasAbility1 extends EventTriggeredAbility
	{
		public NicolBolasAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Nicol Bolas unless you pay (U)(B)(R).");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory payMana = new EventFactory(EventType.PAY_MANA, "Pay (U)(B)(R)");
			payMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payMana.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(U)(B)(R)")));
			payMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(unless(You.instance(), sacrificeThis("Nicol Bolas"), payMana, "Sacrifice Nicol Bolas unless you pay (U)(B)(R)."));
		}
	}

	public static final class NicolBolasAbility2 extends EventTriggeredAbility
	{
		public NicolBolasAbility2(GameState state)
		{
			super(state, "Whenever Nicol Bolas deals damage to an opponent, that player discards his or her hand.");
			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(discardHand(thatPlayer, "That player discards his or her hand."));
		}
	}

	public NicolBolas(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, sacrifice Nicol Bolas unless you pay
		// (U)(B)(R).
		this.addAbility(new NicolBolasAbility1(state));

		// Whenever Nicol Bolas deals damage to an opponent, that player
		// discards his or her hand.
		this.addAbility(new NicolBolasAbility2(state));
	}
}
