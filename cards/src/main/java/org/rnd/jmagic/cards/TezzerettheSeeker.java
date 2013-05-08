package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Tezzeret the Seeker")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.TEZZERET})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class TezzerettheSeeker extends Card
{
	/**
	 * "I'll be back."
	 */
	public static final class T_800 extends LoyaltyAbility
	{
		public T_800(GameState state)
		{
			super(state, +1, "Untap up to two target artifacts.");

			Target target = this.addTarget(ArtifactPermanents.instance(), "up to two target artifacts");
			target.setNumber(0, 2);

			this.addEffect(untap(targetedBy(target), "Untap up to two target artifacts."));
		}
	}

	/**
	 * "Hasta la vista, baby."
	 */
	public static final class T_1000 extends LoyaltyAbility
	{
		public T_1000(GameState state)
		{
			super(state, OtherCost.MINUS_X, "Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.");

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasType.instance(Type.ARTIFACT), HasConvertedManaCost.instance(Between.instance(numberGenerator(0), ValueOfX.instance(This.instance()))))));
			this.addEffect(search);
		}
	}

	/**
	 * "..."
	 */
	public static final class T_X extends LoyaltyAbility
	{
		public T_X(GameState state)
		{
			super(state, -5, "Artifacts you control become 5/5 artifact creatures until end of turn.");

			SetGenerator artifactsYouControl = Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance()));
			SetGenerator five = numberGenerator(5);

			ContinuousEffect.Part ptPart = setPowerAndToughness(artifactsYouControl, five, five);

			ContinuousEffect.Part typePart = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			typePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, artifactsYouControl);
			typePart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ARTIFACT, Type.CREATURE));

			this.addEffect(createFloatingEffect("Artifacts you control become 5/5 artifact creatures until end of turn.", ptPart, typePart));
		}
	}

	public TezzerettheSeeker(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		this.addAbility(new T_800(state));

		this.addAbility(new T_1000(state));

		this.addAbility(new T_X(state));
	}
}
