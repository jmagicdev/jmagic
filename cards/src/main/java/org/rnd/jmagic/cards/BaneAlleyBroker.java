package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bane Alley Broker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("1UB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class BaneAlleyBroker extends Card
{
	public static final class BaneAlleyBrokerAbility0 extends ActivatedAbility
	{
		public BaneAlleyBrokerAbility0(GameState state)
		{
			super(state, "(T): Draw a card, then exile a card from your hand face down.");
			this.costsTap = true;

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));

			EventFactory exile = exile(You.instance(), InZone.instance(HandOf.instance(You.instance())), 1, "then exile a card from your hand face down.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(BaneAlleyBrokerAbility1.class);
			this.getLinkManager().addLinkClass(BaneAlleyBrokerAbility2.class);
		}
	}

	public static final class BaneAlleyBrokerAbility1 extends StaticAbility
	{
		public BaneAlleyBrokerAbility1(GameState state)
		{
			super(state, "You may look at cards exiled with Bane Alley Broker.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.LOOK);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(Identity.instance(this))));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(BaneAlleyBrokerAbility0.class);
		}
	}

	public static final class BaneAlleyBrokerAbility2 extends ActivatedAbility
	{
		public BaneAlleyBrokerAbility2(GameState state)
		{
			super(state, "(U)(B), (T): Return a card exiled with Bane Alley Broker to its owner's hand.");
			this.setManaCost(new ManaPool("(U)(B)"));
			this.costsTap = true;

			this.addEffect(bounceChoice(You.instance(), 1, ChosenFor.instance(LinkedTo.instance(This.instance())), "Return a card exiled with Bane Alley Broker to its owner's hand."));

			this.getLinkManager().addLinkClass(BaneAlleyBrokerAbility0.class);
		}
	}

	public BaneAlleyBroker(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// (T): Draw a card, then exile a card from your hand face down.
		this.addAbility(new BaneAlleyBrokerAbility0(state));

		// You may look at cards exiled with Bane Alley Broker.
		this.addAbility(new BaneAlleyBrokerAbility1(state));

		// (U)(B), (T): Return a card exiled with Bane Alley Broker to its
		// owner's hand.
		this.addAbility(new BaneAlleyBrokerAbility2(state));
	}
}
