package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Chandra, the Firebrand")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.CHANDRA})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class ChandratheFirebrand extends Card
{
	public static final class ChandratheFirebrandAbility0 extends LoyaltyAbility
	{
		public ChandratheFirebrandAbility0(GameState state)
		{
			super(state, +1, "Chandra, the Firebrand deals 1 damage to target creature or player.");
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Chandra, the Firebrand deals 1 damage to target creature or player."));
		}
	}

	public static final class ChandratheFirebrandAbility1 extends LoyaltyAbility
	{
		public ChandratheFirebrandAbility1(GameState state)
		{
			super(state, -2, "When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.");

			SimpleEventPattern youCastSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			youCastSpell.put(EventType.Parameter.PLAYER, You.instance());
			youCastSpell.put(EventType.Parameter.OBJECT, HasType.instance(Type.INSTANT, Type.SORCERY));

			SetGenerator thatSpell = EventResult.instance(TriggerEvent.instance(This.instance()));

			EventFactory copySpell = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy that spell. You may choose new targets for the copy.");
			copySpell.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copySpell.parameters.put(EventType.Parameter.OBJECT, thatSpell);
			copySpell.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory delayedTrigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.");
			delayedTrigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			delayedTrigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(youCastSpell));
			delayedTrigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(copySpell));
			this.addEffect(delayedTrigger);
		}
	}

	public static final class ChandratheFirebrandAbility2 extends LoyaltyAbility
	{
		public ChandratheFirebrandAbility2(GameState state)
		{
			super(state, -6, "Chandra, the Firebrand deals 6 damage to each of up to six target creatures and/or players.");

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "six target creatures and/or players");
			target.setNumber(0, 6);

			this.addEffect(permanentDealDamage(6, targetedBy(target), "Chandra, the Firebrand deals 6 damage to each of up to six target creatures and/or players."));
		}
	}

	public ChandratheFirebrand(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Chandra, the Firebrand deals 1 damage to target creature or
		// player.
		this.addAbility(new ChandratheFirebrandAbility0(state));

		// -2: When you cast your next instant or sorcery spell this turn, copy
		// that spell. You may choose new targets for the copy.
		this.addAbility(new ChandratheFirebrandAbility1(state));

		// -6: Chandra, the Firebrand deals 6 damage to each of up to six target
		// creatures and/or players.
		this.addAbility(new ChandratheFirebrandAbility2(state));
	}
}
