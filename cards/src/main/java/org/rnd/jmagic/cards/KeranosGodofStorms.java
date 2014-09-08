package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.expansions.*;

@Name("Keranos, God of Storms")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("3UR")
@Printings({@Printings.Printed(ex = JourneyIntoNyx.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED, Color.BLUE})
public final class KeranosGodofStorms extends Card
{
	public static final class KeranosGodofStormsAbility1 extends StaticAbility
	{
		public KeranosGodofStormsAbility1(GameState state)
		{
			super(state, "As long as your devotion to blue and red is less than seven, Keranos isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.BLUE, Color.RED));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	/**
	 * Keys are playerIDs, values are the number of cards they've drawn this
	 * turn
	 */
	public static final class DrawTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.Map<Integer, Integer> value = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(value);

		@Override
		protected Tracker<java.util.Map<java.lang.Integer, java.lang.Integer>> clone()
		{
			DrawTracker ret = (DrawTracker)super.clone();
			ret.value = new java.util.HashMap<Integer, Integer>(this.value);
			ret.unmodifiable = java.util.Collections.unmodifiableMap(this.value);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			this.value.clear();
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.DRAW_ONE_CARD;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			int playerID = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID;
			if(this.value.containsKey(playerID))
				this.value.put(playerID, this.value.get(playerID) + 1);
			else
				this.value.put(playerID, 1);
		}
	}

	public static final class YouveDrawnACardThisTurn extends SetGenerator
	{
		private static SetGenerator _instance = new YouveDrawnACardThisTurn();

		public static SetGenerator instance()
		{
			return _instance;
		}

		private YouveDrawnACardThisTurn()
		{
			// singleton generator
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = You.instance().evaluate(state, thisObject).getOne(Player.class);
			if(state.getTracker(DrawTracker.class).getValue(state).containsKey(you.ID))
				return NonEmpty.set;
			return Empty.set;
		}
	}

	public static final class KeranosGodofStormsAbility2 extends StaticAbility
	{
		public KeranosGodofStormsAbility2(GameState state)
		{
			super(state, "Reveal the first card you draw on each of your turns.");

			SetGenerator yourFirstDraw = Not.instance(YouveDrawnACardThisTurn.instance());
			SetGenerator yourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
			SetGenerator yourFirstDrawYourTurn = Both.instance(yourFirstDraw, yourTurn);
			this.canApply = Both.instance(this.canApply, yourFirstDrawYourTurn);
			state.ensureTracker(new DrawTracker());

			SimpleEventPattern drawOneCard = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			drawOneCard.put(EventType.Parameter.PLAYER, You.instance());
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "Reveal the first card you draw on each of your turns.", drawOneCard);

			SetGenerator originalEffect = EventParts.instance(replacement.replacedByThis());

			EventFactory drawAndReveal = new EventFactory(EventType.DRAW_AND_REVEAL, "Draw a card and reveal it");
			drawAndReveal.parameters.put(EventType.Parameter.EVENT, originalEffect);
			drawAndReveal.setLink(this);
			replacement.addEffect(drawAndReveal);

			this.addEffectPart(replacementEffectPart(replacement));

			this.getLinkManager().addLinkClass(LandEqualsDraw.class);
			this.getLinkManager().addLinkClass(NonlandEqualsBolt.class);
		}
	}

	public static final class LandEqualsDraw extends EventTriggeredAbility
	{
		public LandEqualsDraw(GameState state)
		{
			super(state, "Whenever you reveal a land card this way, draw a card.");
			this.getLinkManager().addLinkClass(KeranosGodofStormsAbility2.class);

			SimpleEventPattern drawALand = new SimpleEventPattern(EventType.DRAW_AND_REVEAL);
			drawALand.withResult(new ZoneChangeContaining(HasType.instance(Type.LAND)));
			this.addPattern(new LinkedEventPattern(drawALand, LinkedTo.instance(This.instance())));

			this.addEffect(drawACard());
		}
	}

	public static final class NonlandEqualsBolt extends EventTriggeredAbility
	{
		public NonlandEqualsBolt(GameState state)
		{
			super(state, "Whenever you reveal a nonland card this way, Keranos deals 3 damage to target creature or player.");
			this.getLinkManager().addLinkClass(KeranosGodofStormsAbility2.class);

			SetGenerator nonland = RelativeComplement.instance(Cards.instance(), HasType.instance(Type.LAND));

			SimpleEventPattern drawANonland = new SimpleEventPattern(EventType.DRAW_AND_REVEAL);
			drawANonland.withResult(nonland);
			this.addPattern(new LinkedEventPattern(drawANonland, LinkedTo.instance(This.instance())));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(3, targetedBy(target), "Keranos deals 3 damage to target creature or player."));
		}
	}

	public KeranosGodofStorms(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to blue and red is less than seven, Keranos
		// isn't a creature.
		this.addAbility(new KeranosGodofStormsAbility1(state));

		// Reveal the first card you draw on each of your turns. Whenever you
		// reveal a land card this way, draw a card. Whenever you reveal a
		// nonland card this way, Keranos deals 3 damage to target creature or
		// player.
		this.addAbility(new KeranosGodofStormsAbility2(state));
		this.addAbility(new LandEqualsDraw(state));
		this.addAbility(new NonlandEqualsBolt(state));
	}
}
