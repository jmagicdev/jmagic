package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Charm")
@Types({Type.INSTANT})
@ManaCost("RWB")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class MarduCharm extends Card
{
	public MarduCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// • Mardu Charm deals 4 damage to target creature.
		{
			SetGenerator target = targetedBy(this.addTarget(1, CreaturePermanents.instance(), "target creature"));
			this.addEffect(1, spellDealDamage(4, target, "Mardu Charm deals 4 damage to target creature."));
		}

		// • Put two 1/1 white Warrior creature tokens onto the battlefield.
		// They gain first strike until end of turn.
		{
			CreateTokensFactory warrior = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Warrior creature tokens onto the battlefield.");
			warrior.setColors(Color.WHITE);
			warrior.setSubTypes(SubType.WARRIOR);
			EventFactory createTokens = warrior.getEventFactory();
			this.addEffect(2, createTokens);

			SetGenerator tokens = EffectResult.instance(createTokens);
			this.addEffect(2, addAbilityUntilEndOfTurn(tokens, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "They gain first strike until end of turn."));
		}

		// • Target opponent reveals his or her hand. You choose a
		// noncreature, nonland card from it. That player discards that card.
		{
			// Target opponent
			Target target = this.addTarget(3, OpponentsOf.instance(You.instance()), "target opponent");

			// reveals his or her hand.
			SetGenerator cards = InZone.instance(HandOf.instance(targetedBy(target)));
			EventType.ParameterMap revealParameters = new EventType.ParameterMap();
			revealParameters.put(EventType.Parameter.CAUSE, This.instance());
			revealParameters.put(EventType.Parameter.OBJECT, cards);
			this.addEffect(3, new EventFactory(EventType.REVEAL, revealParameters, "Target opponent reveals his or her hand."));

			// You choose a noncreature, nonland card from it. That player
			// discards
			// that card.
			// normally, i wouldn't bother calling out the cards in the
			// opponents'
			// hands, since the DISCARD_FORCE will take care of that, but i need
			// a
			// set to take things away from
			SetGenerator creaturesAndLands = HasType.instance(Identity.instance(Type.CREATURE, Type.LAND));
			SetGenerator choices = RelativeComplement.instance(cards, creaturesAndLands);

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.TARGET, targetedBy(target));
			parameters.put(EventType.Parameter.CHOICE, Identity.instance(choices));
			this.addEffect(3, new EventFactory(EventType.DISCARD_FORCE, parameters, "You choose a noncreature, nonland card from it. That player discards that card."));
		}
	}
}
