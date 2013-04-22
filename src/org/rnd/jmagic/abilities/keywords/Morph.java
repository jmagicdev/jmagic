package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Morph extends Keyword
{
	public static final String COST_TYPE = "Morph";
	public final CostCollection morphCost;

	public Morph(GameState state, String morphCost)
	{
		this(state, "Morph " + morphCost, new CostCollection(COST_TYPE, morphCost));
	}

	private Morph(GameState state, String name, CostCollection morphCost)
	{
		super(state, name);
		this.morphCost = morphCost;
	}

	@Override
	public Morph create(Game game)
	{
		return new Morph(game.physicalState, this.getName(), this.morphCost);
	}

	@Override
	public boolean isMorph()
	{
		return true;
	}

	public static final class CastAction extends PlayerAction
	{
		public CastAction(Game game, GameObject source, Player casting)
		{
			super(game, "Cast face down as a 2/2 creature for (3)", casting, source.ID);
		}

		@Override
		public int getSourceObjectID()
		{
			return this.sourceID;
		}

		@Override
		public boolean perform()
		{
			GameObject toBePlayed = this.game.actualState.get(this.sourceID);
			Player casting = this.actor();

			EventFactory castFactory = new EventFactory(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY, casting + " casts a face-down 2/2 creature for (3).");
			castFactory.parameters.put(EventType.Parameter.PLAYER, Identity.instance(casting));
			castFactory.parameters.put(EventType.Parameter.OBJECT, Identity.instance(toBePlayed));
			castFactory.parameters.put(EventType.Parameter.ALTERNATE_COST, Identity.instance(new ManaPool("3")));
			castFactory.parameters.put(EventType.Parameter.FACE_DOWN, Identity.instance(FaceDownCard.class));

			Event castEvent = castFactory.createEvent(this.game, toBePlayed);
			return castEvent.perform(null, true);
		}

		@Override
		public PlayerInterface.ReversionParameters getReversionReason()
		{
			Player player = this.game.physicalState.get(this.actorID);
			return new PlayerInterface.ReversionParameters("CastMorphAction", player.getName() + " failed to cast a face-down creature.");
		}
	}

	public static final class CastFactory extends SpecialActionFactory
	{
		@Override
		public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
		{
			// TODO : 601.5a If an effect allows a card that's prohibited from
			// being cast to be cast face down, and the face-down spell would
			// not be prohibited, that spell can be cast face down.
			// i.e., "Red spells can't be cast" doesn't prohibit someone from
			// casting a red creature through morph (or Illusionary Mask, for
			// that matter).
			boolean makeAbility = false;
			for(PlayerAction action: state.playerActions)
				if(action instanceof CastSpellAction)
					if(((CastSpellAction)action).toBePlayedID == source.ID)
					{
						makeAbility = true;
						break;
					}

			if(!makeAbility)
				return java.util.Collections.emptySet();

			return java.util.Collections.<PlayerAction>singleton(new CastAction(state.game, source, actor));
		}
	}

	public static final class MorphAbility extends StaticAbility
	{
		public MorphAbility(GameState state)
		{
			super(state, "You may cast this face down as a 2/2 creature for (3).");

			this.canApply = NonEmpty.instance();

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(new CastFactory()));
			this.addEffectPart(part);
		}
	}

	@Override
	protected java.util.List<org.rnd.jmagic.engine.StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new MorphAbility(this.state));
	}

	public static final class TurnFaceUpAction extends PlayerAction
	{
		private final CostCollection morphCost;

		public TurnFaceUpAction(GameObject what, CostCollection morphCost)
		{
			super(what.game, "Turn " + what + " face up.", what.getController(what.game.actualState), what.ID);
			this.morphCost = morphCost;
		}

		@Override
		public int getSourceObjectID()
		{
			return this.sourceID;
		}

		@Override
		public boolean perform()
		{
			GameObject object = this.game.actualState.get(this.sourceID);
			Player playerActing = this.actor();

			if(!this.morphCost.manaCost.isEmpty())
			{
				EventFactory payManaFactory = new EventFactory(EventType.PAY_MANA, playerActing + " pays " + this.morphCost.manaCost);
				payManaFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
				payManaFactory.parameters.put(EventType.Parameter.COST, Identity.instance(this.morphCost.manaCost));
				payManaFactory.parameters.put(EventType.Parameter.PLAYER, Identity.instance(playerActing));

				Event payMana = payManaFactory.createEvent(this.game, object);
				if(!payMana.perform(null, true))
					return false;
			}

			for(EventFactory costFactory: this.morphCost.events)
			{
				Event cost = costFactory.createEvent(this.game, object);
				if(!cost.perform(null, true))
					return false;
			}

			EventFactory turnFaceUpFactory = new EventFactory(EventType.TURN_PERMANENT_FACE_UP, "Turn that object face up.");
			turnFaceUpFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			turnFaceUpFactory.parameters.put(EventType.Parameter.OBJECT, This.instance());

			Event turnFaceUp = turnFaceUpFactory.createEvent(this.game, object);
			turnFaceUp.perform(null, true);
			return true;
		}

		@Override
		public PlayerInterface.ReversionParameters getReversionReason()
		{
			Player player = this.game.physicalState.get(this.actorID);
			GameObject object = this.game.physicalState.get(this.sourceID);
			return new PlayerInterface.ReversionParameters("TurnMorphFaceUpAction", player.getName() + " failed to turn " + object.getName() + " face up.");
		}
	}
}
