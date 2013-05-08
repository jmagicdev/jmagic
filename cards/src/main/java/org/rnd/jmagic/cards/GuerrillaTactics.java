package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Guerrilla Tactics")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ALLIANCES, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GuerrillaTactics extends Card
{
	// When a spell or ability an opponent controls causes you to discard
	// Guerrilla Tactics, Guerrilla Tactics deals 4 damage to target creature or
	// player.
	public static final class DiscardTrigger extends EventTriggeredAbility
	{
		public DiscardTrigger(GameState state)
		{
			super(state, "When a spell or ability an opponent controls causes you to discard Guerrilla Tactics, Guerrilla Tactics deals 4 damage to target creature or player.");

			// could be anywhere after being discarded
			this.canTrigger = NonEmpty.instance();

			SetGenerator anOpponentControls = ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance());
			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SimpleEventPattern youDiscardThis = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			youDiscardThis.put(EventType.Parameter.CAUSE, anOpponentControls);
			youDiscardThis.withResult(new ZoneChangeContaining(thisCard));
			this.addPattern(youDiscardThis);

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(4, targetedBy(target), "Guerrilla Tactics deals 4 damage to target creature or player."));
		}
	}

	public GuerrillaTactics(GameState state)
	{
		super(state);

		// Guerrilla Tactics deals 2 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(2, targetedBy(target), "Guerilla Tactics deals 2 damage to target creature or player."));

		this.addAbility(new DiscardTrigger(state));
	}
}
