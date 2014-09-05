package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Elemental Appeal")
@Types({Type.SORCERY})
@ManaCost("RRRR")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ElementalAppeal extends Card
{
	public ElementalAppeal(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "5");
		this.addAbility(ability);

		CostCollection kickerCost = ability.costCollections[0];

		// Put a 7/1 red Elemental creature token with trample and haste onto
		// the battlefield.
		CreateTokensFactory elemental = new CreateTokensFactory(1, 7, 1, "Put a 7/1 red Elemental creature token with trample and haste onto the battlefield.");
		elemental.setColors(Color.RED);
		elemental.setSubTypes(SubType.ELEMENTAL);
		elemental.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
		elemental.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
		EventFactory factory = elemental.getEventFactory();
		this.addEffect(factory);

		// Exile it at the beginning of the next end step.
		SetGenerator thatToken = EffectResult.instance(factory);
		EventFactory exile = exile(delayedTriggerContext(thatToken), "Exile that creature.");
		EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile it at the beginning of the next end step.");
		exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
		this.addEffect(exileLater);

		// If Elemental Appeal was kicked, that creature gets +7/+0 until end of
		// turn.
		EventFactory biggerToken = ptChangeUntilEndOfTurn(thatToken, +7, +0, "That creature gets +7/+0 until end of turn");

		EventFactory biggerTokenIfKicked = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Elemental Appeal was kicked, that creature gets +7/+0 until end of turn.");
		biggerTokenIfKicked.parameters.put(EventType.Parameter.IF, ThisSpellWasKicked.instance(kickerCost));
		biggerTokenIfKicked.parameters.put(EventType.Parameter.THEN, Identity.instance(biggerToken));
		this.addEffect(biggerTokenIfKicked);
	}
}
