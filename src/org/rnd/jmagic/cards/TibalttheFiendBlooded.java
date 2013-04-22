package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tibalt, the Fiend-Blooded")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.TIBALT})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class TibalttheFiendBlooded extends Card
{
	public static final class TibalttheFiendBloodedAbility0 extends LoyaltyAbility
	{
		public TibalttheFiendBloodedAbility0(GameState state)
		{
			super(state, +1, "Draw a card, then discard a card at random.");

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));

			EventFactory discard = new EventFactory(EventType.DISCARD_RANDOM, "then discard a card at random.");
			discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			discard.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(discard);
		}
	}

	public static final class TibalttheFiendBloodedAbility1 extends LoyaltyAbility
	{
		public TibalttheFiendBloodedAbility1(GameState state)
		{
			super(state, -4, "Tibalt, the Fiend-Blooded deals damage equal to the number of cards in target player's hand to that player.");

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator amount = Count.instance(InZone.instance(HandOf.instance(target)));
			this.addEffect(permanentDealDamage(amount, target, "Tibalt, the Fiend-Blooded deals damage equal to the number of cards in target player's hand to that player."));
		}
	}

	public static final class TibalttheFiendBloodedAbility2 extends LoyaltyAbility
	{
		public TibalttheFiendBloodedAbility2(GameState state)
		{
			super(state, -6, "Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn.");

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of all creatures until end of turn.", controlPart));

			this.addEffect(untap(CreaturePermanents.instance(), "Untap them."));
			this.addEffect(createFloatingEffect("They gain haste until end of turn.", addAbilityToObject(CreaturePermanents.instance(), org.rnd.jmagic.abilities.keywords.Haste.class)));
		}
	}

	public TibalttheFiendBlooded(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(2);

		// +1: Draw a card, then discard a card at random.
		this.addAbility(new TibalttheFiendBloodedAbility0(state));

		// -4: Tibalt, the Fiend-Blooded deals damage equal to the number of
		// cards in target player's hand to that player.
		this.addAbility(new TibalttheFiendBloodedAbility1(state));

		// -6: Gain control of all creatures until end of turn. Untap them. They
		// gain haste until end of turn.
		this.addAbility(new TibalttheFiendBloodedAbility2(state));
	}
}
