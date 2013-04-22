package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Miracle extends Keyword
{
	public static final String COST_TYPE = "Miracle";

	protected CostCollection cost;

	public Miracle(GameState state, String cost)
	{
		super(state, "Miracle " + cost);
		this.cost = new CostCollection(COST_TYPE, cost);
	}

	public Miracle(GameState state, CostCollection cost)
	{
		super(state, "Miracle" + (cost.events.isEmpty() ? " " : "\u2014") + cost);
		this.cost = cost;
	}

	@Override
	public Miracle create(Game game)
	{
		return new Miracle(game.physicalState, this.cost);
	}

	@Override
	public java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new MiracleReveal(this.state));
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new MiracleCast(this.state, this.cost));
	}

	public static final class DrawFirst implements ZoneChangePattern
	{
		private final SetGenerator drawWhat;

		public DrawFirst(SetGenerator what)
		{
			this.drawWhat = what;
		}

		@Override
		public boolean match(ZoneChange z, Identified object, GameState state)
		{
			if(!z.isDraw)
				return false;

			GameObject what = this.drawWhat.evaluate(state, object).getOne(GameObject.class);
			GameObject drawing = state.get(z.oldObjectID);
			if(what.ID != drawing.futureSelf)
				return false;

			Player who = what.getOwner(state);
			if(what.ownerID != who.ID)
				return false;

			Set drawnThisTurn = DrawnThisTurn.instance().evaluate(state, object);
			for(GameObject drawn: drawnThisTurn.getAll(GameObject.class))
				if((drawn.ownerID == who.ID) && (drawn.ID != what.ID))
					return false;

			if(state.currentTurn() == null)
				return false;

			return true;
		}

		@Override
		public boolean looksBackInTime()
		{
			// TODO Auto-generated method stub
			return false;
		}
	}

	public static final class MiracleReveal extends StaticAbility
	{
		public MiracleReveal(GameState state)
		{
			super(state, "You may reveal this card from your hand as you draw it if it's the first card you've drawn this turn.");

			state.ensureTracker(new DrawnThisTurn.DrawTracker());
			ZoneChangeReplacementEffect revealAfterDraw = new ZoneChangeReplacementEffect(state.game, "You may reveal this card from your hand as you draw it if it's the first card you've drawn this turn.");
			revealAfterDraw.addPattern(new DrawFirst(This.instance()));

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal this card from your hand");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, This.instance());
			reveal.parameters.put(EventType.Parameter.EFFECT, Identity.instance(NonEmpty.instance()));
			reveal.setLink(this);

			EventFactory youMayReveal = new EventFactory(EventType.PLAYER_MAY, "You may reveal this card from your hand");
			youMayReveal.parameters.put(EventType.Parameter.PLAYER, You.instance());
			youMayReveal.parameters.put(EventType.Parameter.EVENT, Identity.instance(reveal));
			youMayReveal.parameters.put(EventType.Parameter.TARGET, This.instance());
			revealAfterDraw.afterEffects.add(youMayReveal);

			this.addEffectPart(replacementEffectPart(revealAfterDraw));

			this.canApply = NonEmpty.instance();
			this.getLinkManager().addLinkClass(MiracleCast.class);
		}
	}

	public static final class MiracleCast extends EventTriggeredAbility
	{
		private final CostCollection cost;

		public MiracleCast(GameState state, CostCollection cost)
		{
			// TODO : this text won't work with nonmana miracle costs
			super(state, "When you reveal this card this way, you may cast it by paying " + cost + " rather than its mana cost.");
			this.cost = cost;
			this.getLinkManager().addLinkClass(MiracleReveal.class);

			// this seems redundant, except that this generator will be empty
			// when the card is revealed a different way
			SetGenerator linked = ChosenFor.instance(LinkedTo.instance(This.instance()));

			SimpleEventPattern revealThisWithMiracle = new SimpleEventPattern(EventType.REVEAL);
			revealThisWithMiracle.put(EventType.Parameter.OBJECT, linked);
			this.addPattern(revealThisWithMiracle);

			EventFactory castThis = new EventFactory(EventType.PLAYER_MAY_CAST, "You may cast it by paying " + cost + " rather than its mana cost.");
			castThis.parameters.put(EventType.Parameter.PLAYER, You.instance());
			castThis.parameters.put(EventType.Parameter.ALTERNATE_COST, Identity.instance(cost.getSet()));
			castThis.parameters.put(EventType.Parameter.OBJECT, linked);
			this.addEffect(castThis);

			this.canTrigger = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(HandOf.instance(You.instance())));
		}

		@Override
		public MiracleCast create(Game game)
		{
			return new MiracleCast(game.physicalState, this.cost);
		}
	}
}