package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tezzeret, Agent of Bolas")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.TEZZERET})
@ManaCost("2UB")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class TezzeretAgentofBolas extends Card
{
	public static final class TezzeretAgentofBolasAbility0 extends LoyaltyAbility
	{
		public TezzeretAgentofBolasAbility0(GameState state)
		{
			super(state, 1, "Look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.CARD, TopCards.instance(5, LibraryOf.instance(You.instance())));
			parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.ARTIFACT));
			this.addEffect(new EventFactory(PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND, parameters, "Look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in any order."));
		}
	}

	public static final class TezzeretAgentofBolasAbility1 extends LoyaltyAbility
	{
		public TezzeretAgentofBolasAbility1(GameState state)
		{
			super(state, -1, "Target artifact becomes a 5/5 artifact creature.");

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));

			Animator animate = new Animator(target, 5, 5);
			animate.addType(Type.ARTIFACT);
			animate.removeOldTypes();
			this.addEffect(createFloatingEffect(Empty.instance(), "Target artifact becomes a 5/5 artifact creature.", animate.getParts()));
		}
	}

	public static final class TezzeretAgentofBolasAbility2 extends LoyaltyAbility
	{
		public TezzeretAgentofBolasAbility2(GameState state)
		{
			super(state, -4, "Target player loses X life and you gain X life, where X is twice the number of artifacts you control.");

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator X = Multiply.instance(numberGenerator(2), Count.instance(Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance()))));

			EventFactory loseLife = loseLife(target, X, "Target player loses X life");
			EventFactory gainLife = gainLife(You.instance(), X, "and you gain X life, where X is twice the number of artifacts you control.");
			this.addEffect(simultaneous(loseLife, gainLife));

		}
	}

	public TezzeretAgentofBolas(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Look at the top five cards of your library. You may reveal an
		// artifact card from among them and put it into your hand. Put the rest
		// on the bottom of your library in any order.
		this.addAbility(new TezzeretAgentofBolasAbility0(state));

		// -1: Target artifact becomes a 5/5 artifact creature.
		this.addAbility(new TezzeretAgentofBolasAbility1(state));

		// -4: Target player loses X life and you gain X life, where X is twice
		// the number of artifacts you control.
		this.addAbility(new TezzeretAgentofBolasAbility2(state));
	}
}
