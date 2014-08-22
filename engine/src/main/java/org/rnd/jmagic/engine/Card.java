package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Card extends GameObject implements Castable, PlayableAsLand
{
	private Castable.Simple castableDelegate;
	private PlayableAsLand.Simple playableAsLandDelegate;

	/**
	 * Constructs a card that is blank (except for the name).
	 * 
	 * @param state The game state the card is to exist in.
	 */
	public Card(GameState state)
	{
		super(state);

		this.castableDelegate = new Castable.Simple(this);
		this.playableAsLandDelegate = new PlayableAsLand.Simple(this);
	}

	@Override
	public void addCastablePermissionLocation(PlayPermission permission)
	{
		this.castableDelegate.addCastablePermissionLocation(permission);
	}

	@Override
	public void addCastablePermissionTiming(PlayPermission permission)
	{
		this.castableDelegate.addCastablePermissionTiming(permission);
	}

	@Override
	public void addPlayableAsLandPermissionLocation(PlayPermission permission)
	{
		this.playableAsLandDelegate.addPlayableAsLandPermissionLocation(permission);
	}

	@Override
	public void addPlayableAsLandPermissionTiming(PlayPermission permission)
	{
		this.playableAsLandDelegate.addPlayableAsLandPermissionTiming(permission);
	}

	@Override
	public Card clone(GameState state)
	{
		Card ret = (Card)super.clone(state);
		ret.castableDelegate = this.castableDelegate.clone();
		ret.playableAsLandDelegate = this.playableAsLandDelegate.clone();
		return ret;
	}

	/**
	 * Counters this card.
	 * 
	 * @param counterer The thing countering this card.
	 * @return The countered card (probably in the graveyard).
	 */
	@Override
	public final GameObject counterThisObject(Set counterer)
	{
		return this.counterThisObject(counterer, this.getOwner(this.game.physicalState).getGraveyard(this.game.physicalState));
	}

	@Override
	public final GameObject counterThisObject(Set counterer, Zone counterTo)
	{
		SetGenerator thisObject = IdentifiedWithID.instance(this.ID);

		Event move = new Event(this.game.physicalState, "Put " + this + " in " + counterTo + ".", EventType.MOVE_OBJECTS);
		move.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		move.parameters.put(EventType.Parameter.TO, Identity.instance(counterTo));
		move.parameters.put(EventType.Parameter.OBJECT, thisObject);
		// required for "if ~ would be put into a graveyard..." to work
		move.setSource(this);
		move.perform(null, true);
		return NewObjectOf.instance(move.getResultGenerator()).evaluate(this.game.actualState, null).getOne(GameObject.class);
	}

	@Override
	public final GameObject create(Game game)
	{
		GameObject newObject = super.create(game);
		return newObject;
	}

	/**
	 * 202.3. The converted mana cost of an object is a number equal to the
	 * total amount of mana in its mana cost, regardless of color.
	 * 
	 * @return The converted mana cost of this card.
	 */
	@Override
	public int getConvertedManaCost()
	{
		ManaPool manaCost = this.getManaCost();
		if(manaCost == null)
			// 202.3a The converted mana cost of an object with no mana cost is
			// 0.
			return 0;
		return manaCost.converted();
	}

	/** return True. */
	@Override
	public boolean isCard()
	{
		return true;
	}

	@Override
	public java.util.List<CastSpellAction> getCastActions(GameState state, Player who)
	{
		return this.castableDelegate.getCastActions(state, who, this);
	}

	/**
	 * Determines whether this card is currently a permanent.
	 */
	@Override
	public boolean isPermanent()
	{
		return (!this.isGhost() && this.zoneID == this.game.actualState.battlefield().ID);
	}

	@Override
	public boolean isPlayableAsLandBy(GameState state, Player who)
	{
		return this.playableAsLandDelegate.isPlayableAsLandBy(state, who, this);
	}

	/**
	 * Determines whether this card is currently a spell.
	 */
	@Override
	public boolean isSpell()
	{
		return (this.zoneID == this.game.actualState.stack().ID);
	}

	/**
	 * Puts this card on the stack.
	 * 
	 * @param faceDownValues Null if this card is to be put on the stack face
	 * up; otherwise, a set of values for this object to assume when it's put on
	 * the stack (face down).
	 * @return The new instance of the card on the stack.
	 */
	@Override
	public GameObject putOnStack(Player controller, Class<? extends Characteristics> faceDownValues)
	{
		SetGenerator thisObject = IdentifiedWithID.instance(this.ID);

		Event moveEvent = new Event(this.game.physicalState, "Put " + this + " on the stack.", EventType.PUT_IN_CONTROLLED_ZONE);
		moveEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		moveEvent.parameters.put(EventType.Parameter.CONTROLLER, Identity.instance(controller));
		moveEvent.parameters.put(EventType.Parameter.ZONE, Stack.instance());
		moveEvent.parameters.put(EventType.Parameter.OBJECT, thisObject);
		if(faceDownValues != null)
			moveEvent.parameters.put(EventType.Parameter.FACE_DOWN, Identity.instance(faceDownValues));
		moveEvent.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));

		moveEvent.perform(null, true);
		return this.game.actualState.stack().objects.get(0);
	}

	/**
	 * Resolves this card. For instants and sorceries, this means following the
	 * instructions on the card. For permanent spells, it means putting the card
	 * onto the battlefield.
	 */
	@Override
	public final void resolve()
	{
		SetGenerator thisObject = IdentifiedWithID.instance(this.ID);
		SetGenerator you = ControllerOf.instance(thisObject);

		if(this.getTypes().contains(Type.INSTANT) || this.getTypes().contains(Type.SORCERY))
		{
			this.followInstructions();

			Zone graveyard = ((Card)this.getActual()).getOwner(this.game.actualState).getGraveyard(this.game.actualState);
			Event moveEvent = new Event(this.game.physicalState, "Put " + this + " in " + graveyard + ".", EventType.MOVE_OBJECTS);
			moveEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			moveEvent.parameters.put(EventType.Parameter.TO, Identity.instance(graveyard));
			moveEvent.parameters.put(EventType.Parameter.OBJECT, thisObject);
			moveEvent.parameters.put(EventType.Parameter.RESOLVING, Empty.instance());

			Event putIntoGraveyard = moveEvent;
			// required for "if ~ would be put into a graveyard..." to work
			putIntoGraveyard.setSource(this);
			putIntoGraveyard.perform(null, true);
		}
		else
		{
			// 608.3. If the object that's resolving is a permanent spell, its
			// resolution involves a single step (unless it's an Aura). The
			// spell card becomes a permanent and is put onto the battlefield
			// under the control of the spell's controller.

			// 608.3a If the object that's resolving is an Aura spell, its
			// resolution involves two steps. ...

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put " + this + " onto the battlefield.");
			if(this.getSubTypes().contains(SubType.AURA))
			{
				// ... First, it checks whether the target specified by its
				// enchant ability is still legal, as described in rule 608.2b.
				// ...
				// This assumes that the only mode of an Aura is the mode
				// declaring what to target and that only the first target in
				// that mode is relevant
				Target target = this.getChosenTargets().get(this.getMode(1).targets.iterator().next()).get(0);
				if(!target.isLegal(this.game, this))
				{
					Event counterEvent = new Event(this.game.physicalState, "Counter " + this + " due to lack of legal target.", EventType.COUNTER);
					counterEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
					counterEvent.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(this.ID));
					counterEvent.perform(null, true);
					return;
				}

				// ... If so, the spell card becomes a permanent and is put onto
				// the battlefield under the control of the spell's controller
				// attached to the object it was targeting.
				factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO, "Put " + this + " onto the battlefield attached to the object it was targeting.");
				factory.parameters.put(EventType.Parameter.TARGET, ExtractTargets.instance(Identity.instance(target)));
			}
			factory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, you);
			factory.parameters.put(EventType.Parameter.OBJECT, thisObject);
			factory.parameters.put(EventType.Parameter.RESOLVING, Empty.instance());

			Event putOntoBattlefield = factory.createEvent(this.game, this);
			putOntoBattlefield.perform(null, true);
		}
	}

	@Override
	void setManaCost(ManaPool manaCost)
	{
		super.setManaCost(manaCost);
		this.setColors();
	}
}
