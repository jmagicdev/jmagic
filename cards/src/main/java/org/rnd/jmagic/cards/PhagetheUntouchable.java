package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Phage the Untouchable")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR, SubType.MINION})
@ManaCost("3BBBB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Legions.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PhagetheUntouchable extends Card
{
	public static final Game.LoseReason PHAGE_LOSE_REASON = new Game.LoseReason("PhageTheUntouchable");

	public static final class Wham extends EventTriggeredAbility
	{
		public Wham(GameState state)
		{
			super(state, "When Phage the Untouchable enters the battlefield, if you didn't cast it from your hand, you lose the game.");

			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator thisCardAsItWasCast = OldObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator youCastThis = Intersect.instance(PlayerCasting.instance(thisCardAsItWasCast), You.instance());
			SetGenerator yourHand = HandOf.instance(You.instance());
			SetGenerator thisCastFromYourHand = Intersect.instance(ZoneCastFrom.instance(thisCardAsItWasCast), yourHand);
			SetGenerator youCastThisFromYourHand = Both.instance(youCastThis, thisCastFromYourHand);
			SetGenerator youDidntCastThisFromYourHand = Not.instance(youCastThisFromYourHand);
			this.interveningIf = youDidntCastThisFromYourHand;

			EventType.ParameterMap loseGameParameters = new EventType.ParameterMap();
			loseGameParameters.put(EventType.Parameter.CAUSE, Union.instance(ABILITY_SOURCE_OF_THIS, Identity.instance(PHAGE_LOSE_REASON)));
			loseGameParameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.LOSE_GAME, loseGameParameters, "You lose the game"));
		}
	}

	public static final class Bam extends EventTriggeredAbility
	{
		public Bam(GameState state)
		{
			super(state, "Whenever Phage deals combat damage to a creature, destroy that creature. It can't be regenerated.");

			this.addPattern(whenDealsCombatDamageToACreature(ABILITY_SOURCE_OF_THIS));

			this.addEffects(bury(this, TakerOfDamage.instance(TriggerDamage.instance(This.instance())), "Destroy that creature. It can't be regenerated."));
		}
	}

	public static final class ThankYouMaam extends EventTriggeredAbility
	{
		public ThankYouMaam(GameState state)
		{
			super(state, "Whenever Phage deals combat damage to a player, that player loses the game.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			EventFactory loseFactory = new EventFactory(EventType.LOSE_GAME, "That player loses the game");
			loseFactory.parameters.put(EventType.Parameter.CAUSE, Union.instance(ABILITY_SOURCE_OF_THIS, Identity.instance(PHAGE_LOSE_REASON)));
			loseFactory.parameters.put(EventType.Parameter.PLAYER, TakerOfDamage.instance(TriggerDamage.instance(This.instance())));
			this.addEffect(loseFactory);
		}
	}

	public PhagetheUntouchable(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new Wham(state));
		this.addAbility(new Bam(state));
		this.addAbility(new ThankYouMaam(state));
	}
}
