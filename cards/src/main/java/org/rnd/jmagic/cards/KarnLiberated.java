package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Karn Liberated")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.KARN})
@ManaCost("7")
@ColorIdentity({})
public final class KarnLiberated extends Card
{
	public static final class KarnLiberatedAbility0 extends LoyaltyAbility
	{
		public KarnLiberatedAbility0(GameState state)
		{
			super(state, +4, "Target player exiles a card from his or her hand.");
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator hand = InZone.instance(HandOf.instance(target));
			SetGenerator cardsInHand = Intersect.instance(Cards.instance(), hand);
			EventFactory exileCard = exile(target, cardsInHand, 1, "Target player exiles a card from his or her hand.");
			exileCard.setLink(this);
			this.addEffect(exileCard);

			this.getLinkManager().addLinkClass(KarnLiberatedAbility2.class);
		}
	}

	public static final class KarnLiberatedAbility1 extends LoyaltyAbility
	{
		public KarnLiberatedAbility1(GameState state)
		{
			super(state, -3, "Exile target permanent.");
			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

			EventFactory exilePermanent = exile(target, "target permanent");
			exilePermanent.setLink(this);
			this.addEffect(exilePermanent);

			this.getLinkManager().addLinkClass(KarnLiberatedAbility2.class);
		}
	}

	public static final class KarnLiberatedAbility2 extends LoyaltyAbility
	{
		public KarnLiberatedAbility2(GameState state)
		{
			super(state, -14, "Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated. Then put those cards onto the battlefield under your control.");

			SetGenerator exiledCards = ChosenFor.instance(LinkedTo.instance(This.instance()));
			SetGenerator nonAura = RelativeComplement.instance(exiledCards, HasSubType.instance(SubType.AURA));
			SetGenerator nonAuraPermanents = Intersect.instance(nonAura, HasType.instance(Type.permanentTypes()), Cards.instance());

			EventFactory restart = new EventFactory(EventType.RESTART_THE_GAME, "Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated.");
			restart.parameters.put(EventType.Parameter.OBJECT, nonAuraPermanents);
			this.addEffect(restart);

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Then put those cards onto the battlefield under your control.");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, InZone.instance(ExileZone.instance()));
			this.addEffect(putOntoBattlefield);

			this.getLinkManager().addLinkClass(KarnLiberatedAbility0.class);
			this.getLinkManager().addLinkClass(KarnLiberatedAbility1.class);
		}
	}

	public KarnLiberated(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(6);

		// +4: Target player exiles a card from his or her hand.
		this.addAbility(new KarnLiberatedAbility0(state));

		// -3: Exile target permanent.
		this.addAbility(new KarnLiberatedAbility1(state));

		// -14: Restart the game, leaving in exile all non-Aura permanent cards
		// exiled with Karn Liberated. Then put those cards onto the battlefield
		// under your control.
		this.addAbility(new KarnLiberatedAbility2(state));
	}
}
