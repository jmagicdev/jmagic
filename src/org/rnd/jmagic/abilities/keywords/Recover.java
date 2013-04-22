package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public abstract class Recover extends Keyword
{
	public Recover(GameState state, String costName)
	{
		super(state, "Recover - " + costName);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new RecoverAbility(this.state, this));

		return ret;
	}

	/**
	 * Child classes will use these to create the event for their cost on demand
	 */
	protected abstract EventFactory getFactory(SetGenerator thisAbility);

	/**
	 * Child classes will use this to specify what the mana cost is
	 */
	protected abstract String getManaCostString();

	public static final class RecoverAbility extends EventTriggeredAbility
	{
		private Recover parent;

		public RecoverAbility(GameState state, Recover parent)
		{
			super(state, "When a creature is put into your graveyard from play, you may pay the recover cost. If you do, return this card from your graveyard to your hand. Otherwise, exile this card.");
			this.parent = parent;
			this.triggersFromGraveyard();

			EventFactory cost = parent.getFactory(This.instance());
			String mana = parent.getManaCostString();

			SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator ownersGraveyard = GraveyardOf.instance(owner);

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), ownersGraveyard, HasType.instance(Type.CREATURE), true));

			if((cost != null && mana != null) || (cost == null && mana == null))
			{
				throw new UnsupportedOperationException("Recover must have either a non-null event cost or a non-null mana cost, but not both");
			}

			EventFactory mayCost = null;

			if(cost != null)
			{
				mayCost = youMay(cost, "You may " + cost.name);
			}
			else
			{
				mayCost = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay " + mana + ".");
				mayCost.parameters.put(EventType.Parameter.CAUSE, This.instance());
				mayCost.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool(mana)));
				mayCost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			}

			EventFactory thenFactory = new EventFactory(EventType.MOVE_OBJECTS, "Return this card from your graveyard to your hand");
			thenFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			thenFactory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			thenFactory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			EventFactory elseFactory = new EventFactory(EventType.MOVE_OBJECTS, "Exile this card");
			elseFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			elseFactory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			elseFactory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, (mayCost.name + ". If you do, return this card from your graveyard to your hand. Otherwise, exile this card."));
			ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayCost));
			ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(thenFactory));
			ifFactory.parameters.put(EventType.Parameter.ELSE, Identity.instance(elseFactory));
			this.addEffect(ifFactory);
		}

		@Override
		public RecoverAbility create(Game game)
		{
			return new RecoverAbility(game.physicalState, this.parent);
		}
	}

	public static final class ForMana extends Recover
	{
		private final String recoverCost;

		public ForMana(GameState state, String manaCost)
		{
			super(state, "Pay " + manaCost);

			this.recoverCost = manaCost;
		}

		@Override
		public ForMana create(Game game)
		{
			return new ForMana(game.physicalState, this.recoverCost);
		}

		@Override
		protected String getManaCostString()
		{
			return this.recoverCost;
		}

		@Override
		protected EventFactory getFactory(SetGenerator thisAbility)
		{
			return null;
		}
	}
}
