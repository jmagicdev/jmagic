package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Duskmantle Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("UB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DuskmantleGuildmage extends Card
{
	public static final class DuskmantleGuildmageAbility0 extends ActivatedAbility
	{
		public DuskmantleGuildmageAbility0(GameState state)
		{
			super(state, "(1)(U)(B): Whenever a card is put into an opponent's graveyard from anywhere this turn, that player loses 1 life.");
			this.setManaCost(new ManaPool("(1)(U)(B)"));

			SetGenerator opponentsYards = GraveyardOf.instance(OpponentsOf.instance(You.instance()));
			SimpleZoneChangePattern toYard = new SimpleZoneChangePattern(null, opponentsYards, Cards.instance(), false);

			SetGenerator thatPlayer = OwnerOf.instance(NewObjectOf.instance(TriggerZoneChange.instance(This.instance())));
			EventFactory loseLife = loseLife(thatPlayer, 1, "That player loses 1 life.");

			EventFactory delayedTrigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Whenever a card is put into an opponent's graveyard from anywhere this turn, that player loses 1 life.");
			delayedTrigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			delayedTrigger.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(toYard));
			delayedTrigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(loseLife));
			delayedTrigger.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(EndMostFloatingEffects.instance()));
			this.addEffect(delayedTrigger);
		}
	}

	public static final class DuskmantleGuildmageAbility1 extends ActivatedAbility
	{
		public DuskmantleGuildmageAbility1(GameState state)
		{
			super(state, "(2)(U)(B): Target player puts the top two cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(2)(U)(B)"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public DuskmantleGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(U)(B): Whenever a card is put into an opponent's graveyard from
		// anywhere this turn, that player loses 1 life.
		this.addAbility(new DuskmantleGuildmageAbility0(state));

		// (2)(U)(B): Target player puts the top two cards of his or her library
		// into his or her graveyard.
		this.addAbility(new DuskmantleGuildmageAbility1(state));
	}
}
