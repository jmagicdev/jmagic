package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Corpse Traders")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CorpseTraders extends Card
{
	public static final class CorpseTradersAbility0 extends ActivatedAbility
	{
		public CorpseTradersAbility0(GameState state)
		{
			super(state, "(2)(B), Sacrifice a creature: Target opponent reveals his or her hand. You choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.addCost(sacrificeACreature());

			// Target opponent reveals his or her hand.
			SetGenerator targetPlayer = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator cardsInHand = InZone.instance(HandOf.instance(targetPlayer));

			EventType.ParameterMap revealParameters = new EventType.ParameterMap();
			revealParameters.put(EventType.Parameter.CAUSE, This.instance());
			revealParameters.put(EventType.Parameter.OBJECT, cardsInHand);
			this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Target opponent reveals his or her hand."));

			// You choose a card from it. That player discards that card.
			EventType.ParameterMap discardParameters = new EventType.ParameterMap();
			discardParameters.put(EventType.Parameter.CAUSE, This.instance());
			discardParameters.put(EventType.Parameter.PLAYER, You.instance());
			discardParameters.put(EventType.Parameter.TARGET, targetPlayer);
			discardParameters.put(EventType.Parameter.CHOICE, Identity.instance(cardsInHand));
			this.addEffect(new EventFactory(EventType.DISCARD_FORCE, discardParameters, "You choose a nonland card from it. That player discards that card."));

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public CorpseTraders(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (2)(B), Sacrifice a creature: Target opponent reveals his or her
		// hand. You choose a card from it. That player discards that card.
		// Activate this ability only any time you could cast a sorcery.
		this.addAbility(new CorpseTradersAbility0(state));
	}
}
