package org.rnd.jmagic.engine;

import java.util.*;
import java.util.stream.*;

import org.rnd.jmagic.engine.PlayerInterface.ChooseParameters;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/** Represents an object that can exist in a zone. */
abstract public class GameObject extends Identified implements AttachableTo, Attackable, Controllable, Sanitizable, Targetable, CanHaveAbilities
{
	private static class BattlefieldProperties implements Cloneable
	{
		/** What the parent GameObject is attached to */
		public int attachedTo;

		/** IDs of the planeswalkers and players this is attacking. */
		public int attackingID;

		/**
		 * Represents "This creature can attack as though it didn't have
		 * defender"
		 */
		public boolean attacksWithDefender;

		/**
		 * If this is null, then that means the creature is in combat and
		 * unblocked. An empty list during combat means the creature was blocked
		 * but not by creatures. A non-empty list is a list of the IDs of all
		 * creatures blocking this. Outside of combat, this will be an empty
		 * list.
		 */
		public java.util.List<Integer> blockedByIDs;

		/** IDs of the creatures this is blocking. */
		public java.util.List<Integer> blockingIDs;

		public MultipleSetPattern cantBeAttachedTo;

		public int damage;

		public boolean damagedByDeathtouchSinceLastSBA;

		/** Whether this can deal combat damage as though it were unblocked */
		public boolean dealDamageAsUnblocked;

		/**
		 * IDs of the creatures attacking this (relevant for planeswalkers
		 * only.)
		 */
		public java.util.Set<Integer> defendingIDs;

		public boolean flipped;

		/** Maximum number of creatures this is allowed to block. */
		public int maximumBlocks;

		public int pairedWith;

		public boolean tapped;

		public boolean transformed;

		public BattlefieldProperties()
		{
			this.attachedTo = -1;
			this.attackingID = -1;
			this.attacksWithDefender = false;
			this.blockedByIDs = new java.util.LinkedList<Integer>();
			this.blockingIDs = new java.util.LinkedList<Integer>();
			this.cantBeAttachedTo = null;
			this.damage = 0;
			this.damagedByDeathtouchSinceLastSBA = false;
			this.dealDamageAsUnblocked = false;
			this.defendingIDs = new java.util.HashSet<Integer>();
			this.flipped = false;
			this.maximumBlocks = 1;
			this.pairedWith = -1;
			this.tapped = false;
			this.transformed = false;
		}

		@Override
		public BattlefieldProperties clone()
		{
			// throw new
			// UnsupportedOperationException("Must call clone with a GameState parameter");
			try
			{
				BattlefieldProperties ret = (BattlefieldProperties)super.clone();
				// attachedTo copied by super.clone
				// attackingID copied by super.clone
				if(null != this.blockedByIDs)
					ret.blockedByIDs = new java.util.LinkedList<Integer>(this.blockedByIDs);
				ret.blockingIDs = new java.util.LinkedList<Integer>(this.blockingIDs);
				// damage copied by super.clone
				// damagedByDeathtouchSinceLastSBA copied by super.clone
				ret.defendingIDs = new java.util.HashSet<Integer>(this.defendingIDs);
				// flipped copied by super.clone
				// flippedValues copied by super.clone
				// flippedValuesFrom copied by super.clone
				// maximumBlocks copied by super.clone
				// tapped copied by super.clone
				// transformed copied by super.clone

				// Do not preserve these properties as they're recomputed
				// everytime continuous effects are applied
				ret.attacksWithDefender = false;
				ret.cantBeAttachedTo = null;
				ret.dealDamageAsUnblocked = false;
				ret.pairedWith = -1;
				return ret;
			}
			catch(CloneNotSupportedException e)
			{
				throw new RuntimeException("Clone not supported on Cloneable object?", e);
			}
		}
	}

	public static final EventFactory MANA_COST_FACTORY = new EventFactory.Constant("Pay the mana cost");
	public static final EventFactory TAP_COST_FACTORY = new EventFactory.Constant("(T)");

	public static final EventFactory UNTAP_COST_FACTORY = new EventFactory.Constant("(Q)");

	/**
	 * The IDs of the players who are specifically granted or denied permission
	 * to see the "front side" of this card after continuous effects.
	 */
	private java.util.Map<Integer, Boolean> actualVisibility;

	public java.util.Set<AlternateCost> alternateCosts;
	public java.util.Set<AlternateManaPayment> alternatePayments;

	// This can't be moved into BattlefieldProperties because of Animate Dead
	// and friends
	private java.util.Set<Integer> attachments;

	private Characteristics backFace;

	private BattlefieldProperties battlefieldProperties;

	protected EventFactory beginTheGameConsequence;
	protected EventFactory beginTheGameEffect;

	private Characteristics bottomHalf;

	// This can't be moved into BattlefieldProperties because of Animate Dead
	// and friends
	private MultipleSetPattern cantBeAttachedBy;

	private MultipleSetPattern cantBeTheTargetOf;

	/** If this object is a spell and was cast, the action used to cast it. */
	public CastSpellAction castAction;

	protected Characteristics[] characteristics;

	public int controllerID;

	private java.util.Map<EventFactory, Integer> costsGenerated;

	public java.util.List<Counter> counters;

	/**
	 * If this spell or ability defines a value for X that is in a cost, this
	 * field holds that definition.
	 */
	private SetGenerator definedX;

	java.util.Map<EventFactory, Integer> effectsGenerated;

	/**
	 * If a GameObject has a non-null faceDownValues, it's considered to be
	 * face-down. This only applies to permanents and spells on the stack and is
	 * not what should be set for exiled face-down cards. In that case,
	 * visibility should be directly modified.
	 */
	public Characteristics faceDownValues;

	public boolean flashbackCostPaid;

	/** What GameObject did this become after a move? */
	public int futureSelf;

	public boolean hasACopyEffect;

	// LKI *stab*
	/**
	 * This is set to true if this GameObject represents an object that no
	 * longer exists.
	 */
	private boolean isGhost;

	public java.util.Collection<ManaSymbol> manaCostRestrictions;

	public java.util.Collection<CostCollection> optionalAdditionalCosts;

	public int ownerID;

	/** What GameObject was this before it moved? */
	public int pastSelf;

	/**
	 * The IDs of the players who are specifically granted or denied permission
	 * to see the "front side" of this card before continuous effects.
	 */
	private java.util.Map<Integer, Boolean> physicalVisibility;

	public int playerCasting;

	/**
	 * For objects on the stack, the modes whose instructions will be followed
	 * when this object resolves.
	 */
	public java.util.List<TextChange> textChanges;

	private int timestamp;

	public ManaSymbol.ManaType xRestriction;

	public int zoneCastFrom;

	public int zoneID;

	/**
	 * Constructs a game object that is blank (except for its name.)
	 *
	 * @param state The game state in which this object exists.
	 */
	GameObject(GameState state)
	{
		super(state);

		this.characteristics = new Characteristics[] {new Characteristics()};
		this.characteristics[0].numModes = new Set(new org.rnd.util.NumberRange(1, 1));

		this.alternateCosts = null;
		this.alternatePayments = null;
		this.attachments = new java.util.HashSet<Integer>();
		this.battlefieldProperties = null;
		this.beginTheGameEffect = null;
		this.beginTheGameConsequence = null;
		this.cantBeAttachedBy = null;
		this.cantBeTheTargetOf = null;
		this.castAction = null;
		this.controllerID = -1;
		this.costsGenerated = null;
		this.counters = new java.util.LinkedList<Counter>();
		this.definedX = null;
		this.effectsGenerated = null;
		this.faceDownValues = null;
		this.flashbackCostPaid = false;
		this.futureSelf = -1;
		this.hasACopyEffect = false;
		this.isGhost = false;
		this.optionalAdditionalCosts = new java.util.LinkedList<CostCollection>();
		this.ownerID = -1;
		this.pastSelf = -1;
		this.playerCasting = -1;
		this.textChanges = new java.util.LinkedList<TextChange>();
		// objects receive timestamps when they are moved
		this.timestamp = -1;
		this.actualVisibility = new java.util.HashMap<Integer, Boolean>();
		this.physicalVisibility = new java.util.HashMap<Integer, Boolean>();
		this.xRestriction = ManaSymbol.ManaType.COLORLESS;
		this.manaCostRestrictions = null;
		this.zoneCastFrom = -1;
		this.zoneID = -1;

		Class<? extends GameObject> clazz = this.getClass();

		BackFace backFace = clazz.getAnnotation(BackFace.class);
		if(backFace != null && backFace.value() != null && this.game.actualState != null)
			this.backFace = Characteristics.createFromClass(this.game, backFace.value(), this, false);
		else
			this.backFace = null;

		BottomHalf bottomHalf = clazz.getAnnotation(BottomHalf.class);
		if(bottomHalf != null && bottomHalf.value() != null && this.game.actualState != null)
			this.setBottomHalf(Characteristics.createFromClass(this.game, bottomHalf.value(), this, false, Characteristics.Characteristic.COLOR, Characteristics.Characteristic.MANA_COST));
		else
			this.setBottomHalf(null);

		ManaCost cost = clazz.getAnnotation(ManaCost.class);
		if(cost != null && cost.value() != null)
			this.setManaCost(new ManaPool(cost.value()));
		else
			this.setManaCost(null);

		SuperTypes superTypes = clazz.getAnnotation(SuperTypes.class);
		if(superTypes != null)
			for(SuperType superType: superTypes.value())
				this.characteristics[0].superTypes.add(superType);

		Types types = clazz.getAnnotation(Types.class);
		if(types != null && types.value().length > 0)
			this.addTypes(java.util.EnumSet.of(types.value()[0], types.value()));

		SubTypes subTypes = clazz.getAnnotation(SubTypes.class);
		if(subTypes != null)
			for(SubType subType: subTypes.value())
				this.characteristics[0].subTypes.add(subType);
	}

	/**
	 * @param ability The keyword ability to add.
	 */
	public final boolean addAbility(Keyword ability)
	{
		return this.addAbility(ability, true);
	}

	/**
	 * @param ability The keyword ability to add.
	 * @param apply Whether or not to also add the abilities granted by the
	 * keyword ability.
	 */
	public final boolean addAbility(Keyword ability, boolean apply)
	{
		boolean ret = true;
		for(Characteristics characteristics: this.characteristics)
		{
			characteristics.abilityIDsInOrder.add(ability.ID);
			if(!characteristics.keywordAbilities.add(ability.ID))
				ret = false;
		}

		if(apply)
			ret = ability.apply(this) && ret;

		return ret;
	}

	/**
	 * @param ability The non-static ability to add.
	 */
	public final boolean addAbility(NonStaticAbility ability)
	{
		for(Characteristics characteristics: this.characteristics)
			characteristics.abilityIDsInOrder.add(ability.ID);

		ability.sourceID = this.ID;
		this.addLink(ability);

		boolean ret = true;
		for(Characteristics characteristics: this.characteristics)
			ret = characteristics.nonStaticAbilities.add(ability.ID) && ret;

		return ret;
	}

	/**
	 * @param ability The static ability to add.
	 */
	public final boolean addAbility(StaticAbility ability)
	{
		for(Characteristics characteristics: this.characteristics)
			characteristics.abilityIDsInOrder.add(ability.ID);

		ability.sourceID = this.ID;
		this.addLink(ability);

		boolean ret = true;
		for(Characteristics characteristics: this.characteristics)
			ret = characteristics.staticAbilities.add(ability.ID) && ret;

		return ret;
	}

	@Override
	public void addAttachment(int attachment)
	{
		this.attachments.add(attachment);
	}

	@Override
	public void addAttacker(int attackingID)
	{
		if(null == this.battlefieldProperties)
			throw new UnsupportedOperationException("Attempt to add attacker without battlefield properties");
		this.battlefieldProperties.defendingIDs.add(attackingID);
	}

	public final void addCost(EventFactory factory)
	{
		this.characteristics[0].costs.add(factory);
	}

	/**
	 * Adds an effect to this object. This method is only to be used when this
	 * object "has no modes".
	 *
	 * @param factory Factory to create the effect event.
	 */
	public final void addEffect(EventFactory factory)
	{
		this.addEffect(1, factory);
	}

	/**
	 * Adds an effect to a specific mode of this object.
	 *
	 * @param mode Which mode to add the effect to; the first mode is mode 1.
	 * @param factory Factory to create the effect event.
	 */
	public final void addEffect(int mode, EventFactory factory)
	{
		List<Mode> modes = this.getModes()[0];
		while(modes.size() < mode)
			modes.add(new Mode(this.ID));
		modes.get(mode - 1).addEffect(factory);

		if(!this.getAbilityIDsInOrder().contains(-1))
			this.characteristics[0].abilityIDsInOrder.add(-1);
	}

	public final void addEffects(EventFactory... factories)
	{
		for(EventFactory factory: factories)
			this.addEffect(factory);
	}

	private final void addLink(Linkable a1)
	{
		Linkable.Manager manager1 = a1.getLinkManager();
		java.util.Set<Class<? extends Linkable>> linkClasses = manager1.getLinkClasses();
		if(linkClasses.isEmpty())
			return;

		for(Linkable a2: this.getNonStaticAbilities())
		{
			Linkable.Manager manager2 = a2.getLinkManager();
			if(linkClasses.contains(a2.getClass()) && (null == manager2.getLink(this.state, a1.getClass())))
			{
				manager1.setLink(a2);
				manager2.setLink(a1);
			}
		}

		for(Linkable a2: this.getStaticAbilities())
		{
			Linkable.Manager manager2 = a2.getLinkManager();
			if(linkClasses.contains(a2.getClass()) && (null == manager2.getLink(this.state, a1.getClass())))
			{
				manager1.setLink(a2);
				manager2.setLink(a1);
			}
		}
	}

	public void addSubTypes(java.util.Collection<SubType> subTypes)
	{
		this.characteristics[0].subTypes.addAll(subTypes);
	}

	public void addSuperTypes(java.util.Collection<SuperType> superTypes)
	{
		this.characteristics[0].superTypes.addAll(superTypes);
	}

	protected final Target addTarget(int mode, SetGenerator filter, String name)
	{
		Target target = new Target(filter, name);
		this.addTarget(mode, target);
		return target;
	}

	protected final void addTarget(int mode, Target target)
	{
		List<Mode> modes = this.getModes()[0];
		while(modes.size() < mode)
			modes.add(new Mode(this.ID));
		modes.get(mode - 1).addTarget(target);
	}

	/**
	 * Adds a target to this object.
	 *
	 * @param filter Describes the legal objects, players, or zones that can be
	 * targeted by this target.
	 * @param name The name of this target, including the word 'target', the
	 * targeting restriction, and the number of objects that must be targeted.
	 * Example: "six target creatures". If this spell or ability has multiple
	 * targets, those targets can target the same kinds of objects, and does
	 * different things to those targets, include what it will do to that
	 * target. Example: "target creature to get -3/-0" (see Agony Warp)
	 * @return The Target object that got added.
	 */
	public final Target addTarget(SetGenerator filter, String name)
	{
		Target target = new Target(filter, name);
		this.addTarget(target);
		return target;
	}

	public final void addTarget(Target target)
	{
		this.addTarget(1, target);
	}

	public void addTypes(java.util.Collection<Type> types)
	{
		this.characteristics[0].types.addAll(types);
	}

	/**
	 * Returns a List of GameObject containing either 1 or 2 elements, and
	 * guarantees that the first element is this object and the last element is
	 * this object as it exists in the physical state.
	 */
	public java.util.List<GameObject> andPhysical()
	{
		if(this.state == this.game.physicalState)
			return java.util.Collections.singletonList(this);
		return java.util.Arrays.asList(new GameObject[] {this, this.getPhysical()});
	}

	@Override
	public boolean attackable()
	{
		return this.characteristics[0].types.contains(Type.PLANESWALKER);
	}

	public boolean canAttachTo(Game game, AttachableTo targetObject)
	{
		boolean attachable = false;

		// Creatures can't be attached to things
		if(this.characteristics[0].types.contains(Type.CREATURE))
			return false;

		if(targetObject.cantBeAttachedBy().match(game.actualState, (Identified)targetObject, new Set(this)))
			return false;

		if(this.cantBeAttachedTo().match(game.actualState, this, new Set(targetObject)))
			return false;

		if(this.characteristics[0].subTypes.contains(SubType.AURA))
		{
			// Auras can only be attached to objects they can legally
			// enchant

			org.rnd.jmagic.abilities.keywords.Enchant enchantKeyword = null;

			for(Keyword k: this.getKeywordAbilities())
			{
				if(k.isEnchant())
				{
					enchantKeyword = (org.rnd.jmagic.abilities.keywords.Enchant)k;
					break;
				}
			}

			if(null == enchantKeyword)
				return false;

			if(!enchantKeyword.filter.evaluate(game, this).contains(targetObject))
				return false;

			attachable = true;
		}

		if(this.characteristics[0].subTypes.contains(SubType.EQUIPMENT))
		{
			// Equipment can only be attached to a creature

			if(targetObject instanceof GameObject && !((GameObject)targetObject).characteristics[0].types.contains(Type.CREATURE))
				return false;

			attachable = true;
		}

		if(this.characteristics[0].subTypes.contains(SubType.FORTIFICATION))
		{
			// Fortifications can only be attached to a land

			if(targetObject.isGameObject() && !((GameObject)targetObject).characteristics[0].types.contains(Type.LAND))
				return false;

			attachable = true;
		}

		return attachable;
	}

	/**
	 * @return A pattern describing all the kinds of things that can't be
	 * attached to this object.
	 */
	@Override
	public final SetPattern cantBeAttachedBy()
	{
		return this.cantBeAttachedBy == null ? SetPattern.NEVER_MATCH : this.cantBeAttachedBy;
	}

	/**
	 * Tells this object it can't be attached by some kinds of objects.
	 *
	 * @param restriction SetPattern describing the kind of object that can't
	 * attach to this.
	 */
	@Override
	public final void cantBeAttachedBy(SetPattern restriction)
	{
		if(this.cantBeAttachedBy == null)
			this.cantBeAttachedBy = new MultipleSetPattern(false);
		this.cantBeAttachedBy.addPattern(restriction);
	}

	/**
	 * @return A pattern describing all the kinds of things that this object
	 * can't attach to.
	 */
	public final SetPattern cantBeAttachedTo()
	{
		if(null == this.battlefieldProperties)
			return SetPattern.NEVER_MATCH;
		if(null == this.battlefieldProperties.cantBeAttachedTo)
			return SetPattern.NEVER_MATCH;
		return this.battlefieldProperties.cantBeAttachedTo;
	}

	/**
	 * Tells this object it can't be attached to some kinds of objects.
	 *
	 * @param restriction SetPattern describing the kind of object this can't
	 * attach to.
	 */
	public final void cantBeAttachedTo(SetPattern restriction)
	{
		if(null == this.battlefieldProperties)
			throw new UnsupportedOperationException("Attempt to set can't be attached to without battlefield properties");
		if(null == this.battlefieldProperties.cantBeAttachedTo)
			this.battlefieldProperties.cantBeAttachedTo = new MultipleSetPattern(false);
		else
			this.battlefieldProperties.cantBeAttachedTo.addPattern(restriction);
	}

	/**
	 * @return A pattern describing all the kinds of things this object can't be
	 * the target of.
	 */
	@Override
	public final SetPattern cantBeTheTargetOf()
	{
		return this.cantBeTheTargetOf == null ? SetPattern.NEVER_MATCH : this.cantBeTheTargetOf;
	}

	/**
	 * Tells this object it can't be the target of some kind of spell or
	 * ability.
	 *
	 * @param what The kind of spell or ability this object can't be the target
	 * of.
	 */
	@Override
	public final void cantBeTheTargetOf(SetPattern what)
	{
		if(this.cantBeTheTargetOf == null)
			this.cantBeTheTargetOf = new MultipleSetPattern(false);
		this.cantBeTheTargetOf.addPattern(what);
	}

	public final void clearCosts()
	{
		this.getCosts().clear();
	}

	/**
	 * Removes all targets for this GameObject. This is only meant to be used on
	 * GameObjects which only have one mode.
	 **/
	public final void clearTargets()
	{
		clearTargets(1);
	}

	public final void clearTargets(int modeNum)
	{
		this.getMode(modeNum).targets.clear();
	}

	@Override
	public GameObject clone(GameState state)
	{
		GameObject ret = (GameObject)super.clone(state);
		ret.characteristics = new Characteristics[this.characteristics.length];
		for(int i = 0; i < ret.characteristics.length; ++i)
			ret.characteristics[i] = this.characteristics[i].clone();

		if(this.backFace != null)
			ret.backFace = this.backFace.clone();
		if(this.bottomHalf != null)
			ret.bottomHalf = this.bottomHalf.clone();
		if(this.faceDownValues != null)
			ret.faceDownValues = this.faceDownValues.clone();

		ret.alternateCosts = null;
		ret.alternatePayments = null;
		ret.attachments = new java.util.HashSet<Integer>(this.attachments);
		if(null != this.battlefieldProperties)
			ret.battlefieldProperties = this.battlefieldProperties.clone();
		ret.castAction = this.castAction;
		if(null != this.costsGenerated)
			ret.costsGenerated = new java.util.HashMap<EventFactory, Integer>(this.costsGenerated);
		ret.counters = new java.util.LinkedList<Counter>(this.counters);
		if(null != this.effectsGenerated)
			ret.effectsGenerated = new java.util.HashMap<EventFactory, Integer>(this.effectsGenerated);
		ret.optionalAdditionalCosts = new java.util.LinkedList<CostCollection>(this.optionalAdditionalCosts);

		// "But wait!" you say, thinking that text change effects are
		// re-computed when the state is refreshed, "shouldn't this list just be
		// empty on clone()?" "No!" I say, "since abilities on the stack have
		// physical text change effects!" That's right, folks, physical text
		// change effects.
		ret.textChanges = new java.util.LinkedList<TextChange>(this.textChanges);
		ret.actualVisibility = new java.util.HashMap<Integer, Boolean>(this.actualVisibility);
		ret.physicalVisibility = new java.util.HashMap<Integer, Boolean>(this.physicalVisibility);
		ret.zoneCastFrom = this.zoneCastFrom;

		// these should not be preserved between backups since they're
		// re-computed every time the game state is refreshed.
		ret.cantBeAttachedBy = null;
		ret.cantBeTheTargetOf = null;

		// maximumBlocks will already have the right value from the constructor

		return ret;
	}

	/**
	 * Counters this object.
	 *
	 * @param counterer What is countering this object.
	 * @return The countered object.
	 *
	 */
	public GameObject counterThisObject(Set counterer)
	{
		throw new UnsupportedOperationException("counterThisObject(Set) called on a class that does not define it");
	}

	/**
	 * Counters this object.
	 *
	 * @param counterer What is countering the object.
	 * @param counterTo Where the object will go when it is countered.
	 * @return The countered object.
	 */
	public GameObject counterThisObject(Set counterer, Zone counterTo)
	{
		throw new UnsupportedOperationException("counter(Set,Zone) called on a class that does not define it");
	}

	public GameObject create(Game game)
	{
		return org.rnd.util.Constructor.construct(this.getClass(), new Class[] {GameState.class}, new Object[] {game.physicalState});
	}

	public GameObject createToMove(Zone destination)
	{
		GameObject oldObject = this.getPhysical();
		GameObject newObject = null;
		if(-1 == oldObject.futureSelf)
		{
			newObject = oldObject.create(this.game);
			newObject.ownerID = oldObject.ownerID;
			oldObject.futureSelf = newObject.ID;
			newObject.pastSelf = oldObject.ID;

			if(this.state.battlefield().equals(destination))
				newObject.battlefieldProperties = new BattlefieldProperties();
		}
		else
		{
			newObject = this.game.physicalState.get(oldObject.futureSelf);
		}
		return newObject;
	}

	/**
	 * If this spell or activated ability has a mana cost, alternative cost,
	 * additional cost, and/or activation cost with an (X), [-X], or X in it,
	 * and the value of X is defined by the text of that spell or ability rather
	 * than being chosen by the player playing it, use this method to define
	 * that value.
	 *
	 * @param X The definition of X. If this parameter evaluates to a single
	 * number, X is that number. If it evaluates to no numbers, X is undefined.
	 * If it evaluates to multiple numbers, X is the sum of those numbers. It
	 * will be evaluated with respect to this object.
	 */
	public void defineX(SetGenerator X)
	{
		this.definedX = X;
	}

	/**
	 * If this returns true, then this object should exist in a gamestate (and
	 * thus IdentifiedWithID should find it)
	 */
	public boolean exists()
	{
		return this.zoneID != -1;
	}

	/**
	 * Follows the instructions written on this object. This is to be used by
	 * inherited classes' resolve methods.
	 */
	public void followInstructions()
	{
		// Before performing effects, null-out any targets which are illegal and
		// make sure at least one legal target remains
		boolean hasTarget = false;
		boolean hasLegalTarget = false;
		boolean needRefresh = false;

		GameObject physicalThis = this.getPhysical();

		java.util.Map<Target, java.util.List<Target>> chosenTargets = new java.util.HashMap<>();
		for(java.util.Map<Target, java.util.List<Target>> characteristicChosenTargets: physicalThis.getChosenTargets())
			chosenTargets.putAll(characteristicChosenTargets);

		for(Mode mode: this.getSelectedModes())
		{
			// Don't check modes with no targets
			if(mode.targets.isEmpty())
				continue;

			for(Target possibleTarget: mode.targets)
			{
				if(!chosenTargets.containsKey(possibleTarget))
					continue;

				java.util.ListIterator<Target> i = chosenTargets.get(possibleTarget).listIterator();
				while(i.hasNext())
				{
					// we wait until now to declare that the spell has a target,
					// because if the spell's targets are allowed to not be
					// chosen (e.g. "any number of" or "up to") and none were
					// chosen, the spell is not considered targeted
					hasTarget = true;

					Target chosenTarget = i.next();
					if(chosenTarget.isLegal(this.game, this))
						hasLegalTarget = true;
					else
					{
						// TODO : Nulling the target is great for trying to
						// act on it, but it doesn't keep it from performing any
						// actions (which is something the rules state invalid
						// targets can't do), since we don't actually check the
						// CAUSE or SOURCE parameter in any of our event types.
						// -RulesGuru
						i.set(null);
						needRefresh = true;
					}
				}
			}
		}

		if(hasTarget && !hasLegalTarget)
		{
			Event counterEvent = new Event(this.game.physicalState, "Counter " + this + " due to lack of legal targets.", EventType.COUNTER);
			counterEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			counterEvent.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(this.ID));
			counterEvent.perform(null, true);
			return;
		}

		if(needRefresh)
			this.game.refreshActualState();
		for(Mode mode: this.getSelectedModes())
			for(EventFactory factory: mode.effects)
				factory.createEvent(this.game, this).perform(null, true);
	}

	/**
	 * @return The IDs of the abilities of this object, in the order they appear
	 * in the text of the object. If the object has a spell ability, -1 is used
	 * as a placeholder for that ability.
	 */
	public java.util.List<Integer> getAbilityIDsInOrder()
	{
		return java.util.Collections.unmodifiableList(this.characteristics[0].abilityIDsInOrder);
	}

	@Override
	public GameObject getActual()
	{
		return (GameObject)super.getActual();
	}

	public int getAttachedTo()
	{
		if(null == this.battlefieldProperties)
			return -1;
		return this.battlefieldProperties.attachedTo;
	}

	@Override
	public java.util.Collection<Integer> getAttachments()
	{
		return new java.util.LinkedList<Integer>(this.attachments);
	}

	public int getAttackingID()
	{
		if(null == this.battlefieldProperties)
			return -1;
		return this.battlefieldProperties.attackingID;
	}

	public Characteristics getBackFace()
	{
		return this.backFace;
	}

	public java.util.List<Integer> getBlockedByIDs()
	{
		if(null == this.battlefieldProperties)
			return java.util.Collections.emptyList();
		return this.battlefieldProperties.blockedByIDs;
	}

	public java.util.List<Integer> getBlockingIDs()
	{
		if(null == this.battlefieldProperties)
			return java.util.Collections.emptyList();
		return this.battlefieldProperties.blockingIDs;
	}

	public Characteristics[] getCharacteristics()
	{
		for(Characteristics characteristics: this.characteristics)
			if(null == characteristics.name || characteristics.name.isEmpty())
				characteristics.name = this.getName();
		return this.characteristics;
	}

	@SuppressWarnings("unchecked")
	public java.util.Map<Target, java.util.List<Target>>[] getChosenTargets()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.chosenTargets).toArray(size -> new java.util.Map[size]);
	}

	public java.util.Set<Color> getColorIndicator()
	{
		return this.characteristics[0].colorIndicator;
	}

	public java.util.Set<Color> getColors()
	{
		return this.characteristics[0].colors;
	}

	/**
	 * @return The controller of this object; or its owner if it has no
	 * controller.
	 *
	 * Controller glossary entry: "... If anything asks for the controller of an
	 * object that doesn't have a controller, use its owner instead."
	 */
	@Override
	public Player getController(GameState state)
	{
		if(this.controllerID == -1)
			return this.getOwner(state);
		return state.get(this.controllerID);
	}

	/** @return The converted mana cost of this object. */
	public abstract int[] getConvertedManaCost();

	/**
	 * Get a cost generated during CAST_SPELL_OR_ACTIVATED_ABILITY
	 *
	 * @param state The {@link GameState} to look up the cost in
	 * @param factory What {@link EventFactory} generated the cost
	 * @return The {@link Event} generated as an cost or {@code null} if nothing
	 * was generated for the given {@code factory}
	 */
	public final Event getCostGenerated(GameState state, EventFactory factory)
	{
		if((null != this.costsGenerated) && this.costsGenerated.containsKey(factory))
			return state.get(this.costsGenerated.get(factory));
		return null;
	}

	public java.util.Collection<EventFactory> getCosts()
	{
		return this.characteristics[0].costs;
	}

	public java.util.Collection<Event> getCostsPaid()
	{
		if(this.costsGenerated == null)
			return java.util.Collections.emptyList();

		java.util.List<Event> ret = new java.util.LinkedList<Event>();
		for(java.util.Map.Entry<EventFactory, Integer> entry: this.costsGenerated.entrySet())
			ret.add(this.state.<Event>get(entry.getValue()));
		return ret;
	}

	public int getDamage()
	{
		if(null == this.battlefieldProperties)
			return 0;
		return this.battlefieldProperties.damage;
	}

	public java.util.Set<Integer> getDefendingIDs()
	{
		if(null == this.battlefieldProperties)
			return java.util.Collections.emptySet();
		return this.battlefieldProperties.defendingIDs;
	}

	/**
	 * If this object doesn't define X to be a specific value, return -1. If it
	 * does, return that value. If the value is undefined (for example, if
	 * Prototype Portal has no cards exiled), return -2.
	 */
	public int getDefinedX()
	{
		if(this.definedX == null)
			return -1;
		Set Xset = this.definedX.evaluate(this.game, this);
		if(Xset.isEmpty())
			return -2;
		return Sum.get(Xset);
	}

	/**
	 * Gets the number this object asks the player to divide when it's played.
	 * This method is only for use on objects that "have no modes".
	 *
	 * Example: Bogardan Hellkite's ability should return Identity.instance(new
	 * Set(5)).
	 *
	 * Example: Living Inferno's ability should return PowerOf(this).
	 */
	public final SetGenerator getDivisionAmount()
	{
		int totalNumSelectedModes = java.util.Arrays.stream(this.characteristics).mapToInt(t -> t.selectedModeNumbers.size()).sum();
		if(totalNumSelectedModes > 1)
			throw new UnsupportedOperationException("Objects with multiple modes should explicitly declare which modes division amount they want");
		return getDivision(1);
	}

	/**
	 * Gets the number and kind of thing ("damage", "counters", etc) this object
	 * asks the player to divide when it's played with the specified mode
	 * chosen.
	 *
	 * @param modeNum The mode.
	 * @return A set generator that evaluates to the division.
	 */
	public final SetGenerator getDivision(int modeNum)
	{
		return this.getMode(modeNum).division;
	}

	/**
	 * Get a effect generated during {@link #followInstructions()}
	 *
	 * @param state The {@link GameState} to look up the effect in
	 * @param factory What {@link EventFactory} generated the effect
	 * @return The {@link Event} generated as an effect or {@code null} if
	 * nothing was generated for the given {@code factory}
	 */
	public final Event getEffectGenerated(GameState state, EventFactory factory)
	{
		if((null != this.effectsGenerated) && this.effectsGenerated.containsKey(factory))
			return state.get(this.effectsGenerated.get(factory));
		return null;
	}

	public Characteristics getBottomHalf()
	{
		return this.bottomHalf;
	}

	// public Class<? extends FlipBottomHalf> getFlippedValuesFrom()
	// {
	// if(null == this.battlefieldProperties)
	// return null;
	// return this.battlefieldProperties.flippedValuesFrom;
	// }

	@Override
	public java.util.List<Keyword> getKeywordAbilities()
	{
		java.util.List<Integer> ids = new java.util.LinkedList<>();
		for(Characteristics characteristics: this.characteristics)
			ids.addAll(characteristics.keywordAbilities);
		return new IDList<>(this.state, ids, false);
	}

	/**
	 * @return the manaCost
	 */
	public ManaPool[] getManaCost()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.manaCost).toArray(size -> new ManaPool[size]);
	}

	public int getMaximumBlocks()
	{
		if(null == this.battlefieldProperties)
			return 1;
		return this.battlefieldProperties.maximumBlocks;
	}

	public int[] getMinimumX()
	{
		return java.util.Arrays.stream(this.characteristics).mapToInt(t -> t.minimumX).toArray();
	}

	/**
	 * @param modeNumber Which mode to get
	 * @return The modeNumber'th mode, with the first mode being modeNumber 1
	 */
	public Mode getMode(int modeNumber)
	{
		List<Mode> modes = this.getModes()[0];
		while(modeNumber > modes.size())
			modes.add(new Mode(this.ID));
		return modes.get(modeNumber - 1);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<Mode>[] getModes()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.modes).toArray(size -> new java.util.LinkedList[size]);
	}

	@Override
	public java.util.List<NonStaticAbility> getNonStaticAbilities()
	{
		java.util.List<Integer> ids = new java.util.LinkedList<>();
		for(Characteristics characteristics: this.characteristics)
			ids.addAll(characteristics.nonStaticAbilities);
		return new IDList<>(this.state, ids, false);
	}

	public Set[] getNumModes()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.numModes).toArray(size -> new Set[size]);
	}

	public CostCollection[] getAlternateCost()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.alternateCost).toArray(size -> new CostCollection[size]);
	}

	@SuppressWarnings("unchecked")
	public java.util.Collection<CostCollection>[] getOptionalAdditionalCostsChosen()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.optionalAdditionalCostsChosen).toArray(size -> new java.util.Collection[size]);
	}

	/** @return The owner of this object. */
	@Override
	public Player getOwner(GameState state)
	{
		return state.get(this.ownerID);
	}

	/**
	 * @return The {@link GameObject} paired with this object, or null if it
	 * isn't.
	 */
	public GameObject getPairedWith(GameState state)
	{
		if(null == this.battlefieldProperties)
			return null;
		if(state.containsIdentified(this.battlefieldProperties.pairedWith))
			return state.get(this.battlefieldProperties.pairedWith);
		return null;
	}

	/** @return The ghost this object used to be. */
	public GameObject getPastSelf()
	{
		return this.state.get(this.pastSelf);
	}

	/** @return This object as it exists in the physical game state. */
	@Override
	public GameObject getPhysical()
	{
		return (GameObject)super.getPhysical();
	}

	public int getPower()
	{
		return this.characteristics[0].power;
	}

	public int getPrintedLoyalty()
	{
		return this.characteristics[0].loyalty;
	}

	@SuppressWarnings("unchecked")
	public java.util.List<Integer>[] getSelectedModeNumbers()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.selectedModeNumbers).toArray(size -> new List[size]);
	}

	public java.util.List<Mode> getSelectedModes()
	{
		java.util.List<Mode> ret = new java.util.LinkedList<>();
		for(Characteristics characteristics: this.characteristics)
			for(int n: characteristics.selectedModeNumbers)
				ret.add(characteristics.modes.get(n - 1));
		return ret;
	}

	@Override
	public java.util.List<StaticAbility> getStaticAbilities()
	{
		java.util.List<Integer> ids = new java.util.LinkedList<>();
		for(Characteristics characteristics: this.characteristics)
			ids.addAll(characteristics.staticAbilities);
		return new IDList<>(this.state, ids, false);
	}

	public java.util.Set<SubType> getSubTypes()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.subTypes).reduce(java.util.EnumSet.noneOf(SubType.class), (left, right) -> {
			left.addAll(right);
			return left;
		});
	}

	public java.util.Set<SuperType> getSuperTypes()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.superTypes).reduce(java.util.EnumSet.noneOf(SuperType.class), (left, right) -> {
			left.addAll(right);
			return left;
		});
	}

	public int getTimestamp()
	{
		return this.timestamp;
	}

	public int getToughness()
	{
		return this.characteristics[0].toughness;
	}

	public java.util.Set<Type> getTypes()
	{
		return java.util.Arrays.stream(this.characteristics).map(t -> t.types).reduce(java.util.EnumSet.noneOf(Type.class), (left, right) -> {
			left.addAll(right);
			return left;
		});
	}

	public int getValueOfX()
	{
		return this.characteristics[0].valueOfX;
	}

	public java.util.Set<Player> getVisibleTo()
	{
		if((this.state == this.game.physicalState))
			return this.getActual().getVisibleTo(true);
		return getVisibleTo(false);
	}

	private java.util.Set<Player> getVisibleTo(boolean physical)
	{
		java.util.Set<Player> ret = new java.util.HashSet<Player>();
		for(Player player: this.state.players)
			if(this.isVisibleTo(player, physical))
				ret.add(player);
		return ret;
	}

	/** @return What zone this object is in. */
	public Zone getZone()
	{
		if(this.zoneID == -1)
			return null;
		return this.game.actualState.get(this.zoneID);
	}

	/** Turns this object into a ghost, and all of its abilities as well. */
	public void ghost()
	{
		this.isGhost = true;

		GameObject actual = this.game.actualState.get(this.ID);
		if(!actual.isGhost())
		{
			actual.ghost();
			return;
		}

		for(NonStaticAbility a: this.getNonStaticAbilities())
			a.ghost();
		for(Keyword a: this.getKeywordAbilities())
			a.ghost();

		if(actual.state != this.game.physicalState)
			actual.clone(this.game.physicalState);
		else
			this.game.physicalState.put(actual);

		// Static abilities aren't ghosted since they aren't GameObjects, so we
		// need to freeze the actual static ability into the physical state when
		// we ghost this object.
		for(StaticAbility a: this.getStaticAbilities())
		{
			if(a.state != this.game.physicalState)
				this.game.actualState.<Identified>get(a.ID).clone(this.game.physicalState);
			else
				this.game.physicalState.put(a);
		}
	}

	/** @return Whether this object has the specified ability. */
	public final boolean hasAbility(Class<? extends Keyword> ability)
	{
		for(Keyword k: this.getKeywordAbilities())
			if(ability.isAssignableFrom(k.getClass()))
				return true;

		return false;
	}

	public boolean isAttacksWithDefender()
	{
		if(null == this.battlefieldProperties)
			return false;
		return this.battlefieldProperties.attacksWithDefender;
	}

	/** @return Whether this object is a card. */
	public boolean isCard()
	{
		return false;
	}

	public boolean isDamagedByDeathtouchSinceLastSBA()
	{
		if(null == this.battlefieldProperties)
			return false;
		return this.battlefieldProperties.damagedByDeathtouchSinceLastSBA;
	}

	public boolean isDealDamageAsUnblocked()
	{
		if(null == this.battlefieldProperties)
			return false;
		return this.battlefieldProperties.dealDamageAsUnblocked;
	}

	public boolean isFlipped()
	{
		if(null == this.battlefieldProperties)
			return false;
		return this.battlefieldProperties.flipped;
	}

	/** @return True. */
	@Override
	public boolean isGameObject()
	{
		return true;
	}

	/** @return True if this object is a ghost; false otherwise. */
	public boolean isGhost()
	{
		return this.isGhost;
	}

	/**
	 * @return Whether this is a mana ability. ActivatedAbility overrides this
	 * method to automagically return the correct value. Triggered abilities
	 * that are mana abilities will need to specially override this.
	 */
	public boolean isManaAbility()
	{
		return false;
	}

	/**
	 * @return Whether this object is a permanent -- false by default;
	 * overridden by Card and Token.
	 */
	@Override
	public boolean isPermanent()
	{
		return false;
	}

	/**
	 * @return Whether this object is a spell -- false by default; overridden by
	 * Card and SpellCopy
	 */
	public boolean isSpell()
	{
		return false;
	}

	/** @return Whether this object is a copy of a spell. */
	public boolean isSpellCopy()
	{
		return false;
	}

	/**
	 * 702.59b A card is "suspended" if it's in the exile zone, has suspend, and
	 * has a time counter on it.
	 *
	 * @return Whether this object is suspended.
	 */
	public boolean isSuspended()
	{
		if(this.zoneID != this.state.exileZone().ID)
			return false;

		boolean hasCounter = false;
		for(Counter c: this.counters)
			if(c.getType() == Counter.CounterType.TIME)
			{
				hasCounter = true;
				break;
			}

		if(!hasCounter)
			return false;

		boolean hasSuspend = false;
		for(Keyword k: this.getKeywordAbilities())
			if(k.isSuspend())
			{
				hasSuspend = true;
				break;
			}

		return hasSuspend;
	}

	public boolean isTapped()
	{
		if(null == this.battlefieldProperties)
			return false;
		return this.battlefieldProperties.tapped;
	}

	/**
	 * @return Whether this object is a token -- false by default; overridden by
	 * Token
	 */
	public boolean isToken()
	{
		return false;
	}

	public boolean isTransformed()
	{
		if(null == this.battlefieldProperties)
			return false;
		return this.battlefieldProperties.transformed;
	}

	public boolean isVisibleTo(Player whoFor)
	{
		if(this.state == this.game.physicalState)
			return this.getActual().isVisibleTo(whoFor, true);
		return this.isVisibleTo(whoFor, false);
	}

	private boolean isVisibleTo(Player whoFor, boolean physical)
	{
		java.util.Map<Integer, Boolean> map = (physical ? this.physicalVisibility : this.actualVisibility);

		// Entries in the visibility map override rules
		if(map.containsKey(whoFor.ID))
			return map.get(whoFor.ID);

		// 707.5. At any time, you may look at a face-down spell you control on
		// the stack or a face-down permanent you control (even if it's phased
		// out). You can't look at face-down cards in any other zone or
		// face-down spells or permanents controlled by another player.

		// (The "face-down cards in any other zone" part refers to exiled
		// face-down and has nothing to do with the status)
		if(physical && (null != this.faceDownValues))
			return (this.isSpell() || this.isPermanent()) && this.getController(this.state).equals(whoFor);

		// The default visibility rule is whether the zone is hidden or not
		if(this.zoneID == -1)
			// if it's not in a zone it's invisible
			return false;
		return this.getZone().visibleTo.contains(whoFor.ID);
	}

	public void paidCost(EventFactory factory, Event cost)
	{
		if(this.costsGenerated == null)
			this.costsGenerated = new java.util.HashMap<EventFactory, Integer>();
		this.costsGenerated.put(factory, cost.ID);
	}

	public GameObject putOnStack(Player controller, java.util.Set<Integer> characteristicsIndices)
	{
		throw new UnsupportedOperationException("Attempt to put a GameObject on the stack whose derived class doesn't have putOnStack defined");
	}

	/**
	 * Puts this object on the stack face down. Classes overriding this method
	 * should set the controller of the new object to the controller specified
	 * in the parameters to this method. If the new object is put on the stack
	 * through the use of a movement event, this is done by setting the
	 * CONTROLLER parameter of that event; otherwise, call
	 * {@link GameObject#setController(Player)} on the new object.
	 *
	 * @param controller Who will control the object on the stack.
	 * @param faceDownValues a class defining a set of values for this object to
	 * assume when it's put on the stack (face down).
	 * @return The object as it exists on the stack in the actual game state.
	 */
	public GameObject putOnStack(Player controller, Class<? extends Characteristics> faceDownValues)
	{
		throw new UnsupportedOperationException("Attempt to put a GameObject on the stack whose derived class doesn't have putOnStack defined");
	}

	/**
	 * @param ability The keyword ability to remove.
	 */
	public final boolean removeAbility(Keyword ability)
	{
		java.util.Arrays.stream(this.characteristics).forEach(t -> t.abilityIDsInOrder.remove((Integer)ability.ID));

		this.state.removeIdentified(ability.ID);

		Stream<Boolean> booleanMap = java.util.Arrays.stream(this.characteristics).map(t -> t.keywordAbilities.remove((Integer)ability.ID));

		boolean ret = booleanMap.reduce((left, right) -> {
			return left && right;
		}).orElse(true);

		ret = ability.removeFrom(this) && ret;

		return ret;
	}

	/**
	 * @param ability The activated ability to remove.
	 */
	public final boolean removeAbility(NonStaticAbility ability)
	{
		java.util.Arrays.stream(this.characteristics).forEach(t -> t.abilityIDsInOrder.remove((Integer)ability.ID));

		this.state.removeIdentified(ability.ID);

		Stream<Boolean> booleanMap = java.util.Arrays.stream(this.characteristics).map(t -> t.nonStaticAbilities.remove((Integer)ability.ID));

		return booleanMap.reduce((left, right) -> {
			return left && right;
		}).orElse(true);
	}

	/**
	 * @param ability The static ability to remove.
	 */
	public final boolean removeAbility(StaticAbility ability)
	{
		java.util.Arrays.stream(this.characteristics).forEach(t -> t.abilityIDsInOrder.remove((Integer)ability.ID));

		this.state.removeIdentified(ability.ID);

		Stream<Boolean> booleanMap = java.util.Arrays.stream(this.characteristics).map(t -> t.staticAbilities.remove((Integer)ability.ID));

		return booleanMap.reduce((left, right) -> {
			return left && right;
		}).orElse(true);
	}

	public void removeAllAbilities()
	{
		for(Characteristics characteristics: this.characteristics)
		{
			for(int a: characteristics.nonStaticAbilities)
				this.state.removeIdentified(a);
			characteristics.nonStaticAbilities.clear();

			for(int a: characteristics.staticAbilities)
				this.state.removeIdentified(a);
			characteristics.staticAbilities.clear();

			for(int a: characteristics.keywordAbilities)
				this.state.removeIdentified(a);
			characteristics.keywordAbilities.clear();

			characteristics.abilityIDsInOrder.clear();
		}
	}

	@Override
	public void removeAttachment(int attachment)
	{
		this.attachments.remove(attachment);
	}

	@Override
	public void removeAttacker(int attackingID)
	{
		if(null == this.battlefieldProperties)
			throw new UnsupportedOperationException("Attempt to remove attacker without battlefield properties");
		this.battlefieldProperties.defendingIDs.remove(attackingID);
	}

	void removeSubTypes(java.util.Collection<SubType> subTypes)
	{
		java.util.Arrays.stream(this.characteristics).forEach(t -> t.subTypes.removeAll(subTypes));
	}

	void removeSuperTypes(java.util.Collection<SuperType> superTypes)
	{
		java.util.Arrays.stream(this.characteristics).forEach(t -> t.superTypes.removeAll(superTypes));
	}

	void removeTypes(java.util.Collection<Type> types)
	{
		java.util.Arrays.stream(this.characteristics).forEach(t -> t.types.removeAll(types));
	}

	/**
	 * @return A set containing the IDs of the newly selected targets.
	 */
	public java.util.Set<Integer> reselectTargets(Player chooser)
	{
		java.util.Set<Integer> newTargetIDs = new java.util.HashSet<Integer>();

		java.util.Map<Target, List<Target>> chosenTargets = new java.util.HashMap<>();
		for(java.util.Map<Target, List<Target>> characteristicChosenTargets: this.getChosenTargets())
			chosenTargets.putAll(characteristicChosenTargets);

		for(Mode mode: this.getSelectedModes())
		{
			java.util.List<Integer> ignoreThese = new java.util.LinkedList<Integer>();
			ignoreThese.add(this.ID);

			for(Target possibleTarget: mode.targets)
			{
				java.util.ListIterator<Target> i = chosenTargets.get(possibleTarget).listIterator();
				while(i.hasNext())
				{
					Target chosenTarget = i.next();
					Set legalTargetsNow = chosenTarget.legalChoicesNow(this.game, this);
					java.util.Set<Target> targetSet = new java.util.HashSet<Target>();

					for(Identified targetObject: legalTargetsNow.getAll(Identified.class))
						if(!ignoreThese.contains(targetObject.ID) && chosenTarget.targetID != targetObject.ID)
							targetSet.add(new Target(targetObject, chosenTarget));

					// Initial selection of targets made each Target only point
					// to a single object, so no need to let the player pick
					// multiple objects this time around
					java.util.List<Target> targetChoice = chooser.sanitizeAndChoose(this.game.actualState, 1, 1, targetSet, PlayerInterface.ChoiceType.TARGETS, PlayerInterface.ChooseReason.DECLARE_TARGETS);

					for(Target t: targetChoice)
					{
						i.set(t);
						if(chosenTarget.restrictFromLaterTargets)
							ignoreThese.add(t.targetID);
						newTargetIDs.add(t.targetID);
					}
				}
			}
		}

		return newTargetIDs;
	}

	/** Resolves this object. */
	public void resolve()
	{
		throw new UnsupportedOperationException("Attempt to resolve a [" + this.getClass().getSimpleName() + "] for which resolve isn't defined");
	}

	@Override
	public org.rnd.jmagic.sanitized.SanitizedGameObject sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedGameObject(state.<GameObject>get(this.ID), whoFor);
	}

	/**
	 * Causes the player playing this object to select modes.
	 *
	 * @return The 1-based mode indices selected, in ascending order
	 */
	public java.util.List<Integer>[] selectModes()
	{
		Player controller = this.getController(this.state);

		GameObject actual = this.getActual();

		for(int i = 0; i < this.characteristics.length; ++i)
		{
			Characteristics characteristics = this.characteristics[i];

			// 700.2a ... If one of the modes would be illegal (due to an
			// inability
			// to choose legal targets, for example), that mode can't be chosen.
			java.util.List<Mode> chooseFrom = new java.util.LinkedList<Mode>(characteristics.modes);
			java.util.Iterator<Mode> it = chooseFrom.iterator();
			while(it.hasNext())
				if(!it.next().canBeChosen(this.game, this))
					it.remove();

			final ChooseParameters<java.io.Serializable> chooseParameters = new ChooseParameters<java.io.Serializable>(actual.characteristics[i].numModes, PlayerInterface.ChoiceType.MODE, PlayerInterface.ChooseReason.SELECT_MODE);
			chooseParameters.thisID = this.ID;
			java.util.Collection<Mode> choices = controller.sanitizeAndChoose(this.state, chooseFrom, chooseParameters);

			characteristics.selectedModeNumbers = new java.util.LinkedList<Integer>();
			int n = 1;
			for(Mode mode: characteristics.modes)
			{
				if(choices.contains(mode))
					characteristics.selectedModeNumbers.add(n);
				n++;
			}
		}

		return this.getSelectedModeNumbers();
	}

	/**
	 * This function is called by CAST_SPELL_OR_ACTIVATE_ABILITY. Divisions are
	 * NOT assigned during this function. If the number of targets is variable,
	 * this function will ask for the number of targets first.
	 *
	 * TODO : 601.2C ... If any effects say that an object or player must be
	 * chosen as a target, the player chooses targets so that he or she obeys
	 * the maximum possible number of such effects without violating any rules
	 * or effects that say that an object or player can't be chosen as a target.
	 * ...
	 *
	 * @return Whether all required targets could be chosen.
	 */
	public final boolean selectTargets()
	{
		boolean ret = true;

		Player specificChooser = this.getController(this.state);
		java.util.Set<Integer> newTargetIDs = new java.util.HashSet<Integer>();

		java.util.List<Mode>[] modes = this.getActual().getModes();
		java.util.List<Mode> selectedModes = this.getSelectedModes();
		for(int i = 0; i < modes.length; ++i)
			for(Mode mode: modes[i])
			{
				if(!selectedModes.contains(mode))
					continue;

				java.util.List<Integer> ignoreThese = new java.util.LinkedList<Integer>();
				ignoreThese.add(this.ID);

				for(Target target: mode.targets)
				{
					if(target.condition.evaluate(this.game, this).isEmpty())
						continue;

					int previousTarget = -1;

					Set legalTargetsNow = target.legalChoicesNow(this.game, this);
					java.util.Set<Target> targetSet = new java.util.HashSet<Target>();

					for(Identified potentialTarget: legalTargetsNow.getAll(Identified.class))
						if(!ignoreThese.contains(potentialTarget.ID) && previousTarget != potentialTarget.ID)
							targetSet.add(new Target(potentialTarget, target));

					if(target.chooser != null)
						specificChooser = target.chooser.evaluate(this.game, this).getOne(Player.class);

					org.rnd.util.NumberRange number = target.number.evaluate(this.game, this).getOne(org.rnd.util.NumberRange.class);
					int min = number.getLower(0);
					int max = number.getUpper(targetSet.size());
					java.util.List<Target> targetChoice = null;
					while(targetChoice == null)
					{
						targetChoice = specificChooser.sanitizeAndChoose(this.game.actualState, min, max, targetSet, PlayerInterface.ChoiceType.TARGETS, PlayerInterface.ChooseReason.DECLARE_TARGETS);
						if(!target.checkSpecialRestrictions(this.state, targetChoice))
							targetChoice = null;
					}
					if(targetChoice.size() < min)
					{
						this.getChosenTargets()[i].put(target, null);
						ret = false;
						break;
					}

					java.util.List<Target> chosenTargets = new java.util.LinkedList<Target>();
					this.getChosenTargets()[i].put(target, chosenTargets);
					int count = 0;
					for(Target t: targetChoice)
					{
						if(1 < targetChoice.size())
						{
							++count;
							t.name = "target " + count + " of " + t.name;
						}
						chosenTargets.add(t);
						if(target.restrictFromLaterTargets)
							ignoreThese.add(t.targetID);
						newTargetIDs.add(t.targetID);
					}
				}
			}

		if(ret && !newTargetIDs.isEmpty())
		{
			EventFactory becomesTarget = new EventFactory(EventType.BECOMES_TARGET, "Changed targets of " + this);
			becomesTarget.parameters.put(EventType.Parameter.OBJECT, Identity.instance(this));
			becomesTarget.parameters.put(EventType.Parameter.TARGET, IdentifiedWithID.instance(newTargetIDs));
			becomesTarget.createEvent(this.game, null).perform(null, true);
		}

		return ret;
	}

	void setAbilityIDsInOrder(java.util.List<Integer>[] abilityIDsInOrder)
	{
		for(int i = 0; i < this.characteristics.length; ++i)
			this.characteristics[i].abilityIDsInOrder = abilityIDsInOrder[i];
	}

	/**
	 * @param player The player we are setting the visibility of this object to
	 * @param visibility true or false to set the players visibility to that;
	 * null to reset it
	 */
	public void setActualVisibility(Player player, Boolean visibility)
	{
		if(visibility == null)
			this.actualVisibility.remove(player.ID);
		else
		{
			this.actualVisibility.put(player.ID, visibility);
			if(visibility && this.state.controlledPlayers.containsKey(player.ID) && this.state.controlledPlayers.get(player.ID) != player.ID)
				this.setActualVisibility(this.state.<Player>get(this.state.controlledPlayers.get(player.ID)), visibility);
		}
	}

	public void setAttachedTo(int attachedTo)
	{
		this.battlefieldProperties.attachedTo = attachedTo;
	}

	public void setAttackingID(int attackingID)
	{
		this.battlefieldProperties.attackingID = attackingID;
	}

	public void setAttacksWithDefender(boolean attacksWithDefender)
	{
		this.battlefieldProperties.attacksWithDefender = attacksWithDefender;
	}

	protected void setBottomHalf(Characteristics bottomHalf)
	{
		this.bottomHalf = bottomHalf;
	}

	public void setBlockedByIDs(java.util.List<Integer> blockedByIDs)
	{
		this.battlefieldProperties.blockedByIDs = blockedByIDs;
	}

	public void setBlockingIDs(java.util.List<Integer> blockingIDs)
	{
		this.battlefieldProperties.blockingIDs = blockingIDs;
	}

	/**
	 * Tells this object that only some of it exists.
	 * 
	 * ...
	 * 
	 * This is for split cards. Giving [0] will cut off the right side, giving
	 * [1] will cut off the left side.
	 * 
	 * @param characteristicsIndices Which characteristics to retain. If this
	 * parameter's size is equal to the number of "sides" this card has, or if
	 * it's null, this method will do nothing.
	 */
	public void selectCharacteristics(java.util.Set<Integer> characteristicsIndices)
	{
		if(characteristicsIndices == null || this.characteristics.length == characteristicsIndices.size())
			return;

		java.util.List<Characteristics> newCharacteristics = new java.util.ArrayList<>();
		for(int i = 0; i < this.characteristics.length; i++)
			if(characteristicsIndices.contains(i))
				newCharacteristics.add(this.characteristics[i]);
			else
				this.characteristics[i].abilityIDsInOrder.stream().forEach(ability -> {
					this.game.actualState.removeIdentified(ability);
				});

		this.characteristics = newCharacteristics.toArray(new Characteristics[] {});
		this.setName(newCharacteristics.stream().map(c -> c.name).reduce((left, right) -> left + " // " + right).orElse(""));
	}

	public void setCharacteristics(Characteristics characteristics)
	{
		java.util.Arrays.stream(this.characteristics).forEach(t -> {
			t.abilityIDsInOrder.stream().forEach(l -> {
				this.game.actualState.removeIdentified(l);
			});
		});

		this.characteristics = new Characteristics[] {characteristics};
		this.setName(characteristics.name);
	}

	public void setColorIndicator(Color... c)
	{
		this.setColorIndicator(java.util.EnumSet.of(c[0], c));
	}

	public void setColorIndicator(java.util.Collection<Color> colors)
	{
		this.setColors(java.util.EnumSet.copyOf(colors));
		this.characteristics[0].colorIndicator = java.util.EnumSet.copyOf(colors);
	}

	/** Sets the colors of this object based on its mana cost. */
	public void setColors()
	{
		for(Characteristics characteristics: this.characteristics)
			if(null != characteristics.manaCost)
			{
				characteristics.colors.clear();
				for(ManaSymbol mana: characteristics.manaCost)
					characteristics.colors.addAll(mana.colors);
			}
	}

	public void setColors(java.util.Set<Color> colors)
	{
		for(Characteristics characteristics: this.characteristics)
			characteristics.colors = colors;
	}

	/**
	 * Tells this object it has a new controller.
	 *
	 * @param controller Null if no one is to control the object; the new
	 * controller otherwise.
	 */
	public void setController(Player controller)
	{
		if(controller == null)
			this.controllerID = -1;
		else
			this.controllerID = controller.ID;
	}

	public void setDamage(int damage)
	{
		this.battlefieldProperties.damage = damage;
	}

	public void setDamagedByDeathtouchSinceLastSBA(boolean damagedByDeathtouchSinceLastSBA)
	{
		this.battlefieldProperties.damagedByDeathtouchSinceLastSBA = damagedByDeathtouchSinceLastSBA;
	}

	public void setDealDamageAsUnblocked(boolean dealDamageAsUnblocked)
	{
		this.battlefieldProperties.dealDamageAsUnblocked = dealDamageAsUnblocked;
	}

	public void setDefendingIDs(java.util.Set<Integer> defendingIDs)
	{
		this.battlefieldProperties.defendingIDs = defendingIDs;
	}

	/**
	 * Tells this object what it should ask the player playing it to divide if
	 * the player has selected the specified mode.
	 *
	 * @param modeNum The mode.
	 * @param division A set generator representing what the player playing this
	 * object will be dividing.
	 */
	public void setDivision(int modeNum, SetGenerator division)
	{
		this.getMode(modeNum).division = division;
	}

	/**
	 * Tells this object what it should ask the player playing it to divide. Use
	 * this method only on objects that "have no modes".
	 *
	 * @param division A set generator representing what the player playing this
	 * object will be dividing.
	 */
	public void setDivision(SetGenerator division)
	{
		this.setDivision(1, division);
	}

	public void setFlipped(boolean flipped)
	{
		this.battlefieldProperties.flipped = flipped;
	}

	/**
	 * @param manaCost the manaCost to set
	 */
	void setManaCost(ManaPool manaCost)
	{
		this.characteristics[0].manaCost = manaCost;
	}

	public void setMaximumBlocks(int maximumBlocks)
	{
		this.battlefieldProperties.maximumBlocks = maximumBlocks;
	}

	protected void setMinimumX(int minimumX)
	{
		this.characteristics[0].minimumX = minimumX;
	}

	/** Assignes this GameObject a new timestamp. */
	public void setNewTimestamp()
	{
		this.timestamp = this.game.physicalState.getNextAvailableTimestamp();
	}

	public void setNumModes(Set numModes)
	{
		this.characteristics[0].numModes = numModes;
	}

	public void setAlternateCost(CostCollection alternateCost)
	{
		this.characteristics[0].alternateCost = alternateCost;
	}

	/**
	 * Tells this object it has a new owner.
	 *
	 * @param owner The new owner.
	 */
	public void setOwner(Player owner)
	{
		if(owner == null)
			throw new UnsupportedOperationException("Attempt to set owner of GameObject to null");
		this.ownerID = owner.ID;
	}

	public void setPairedWith(GameObject object)
	{
		this.battlefieldProperties.pairedWith = object.ID;
	}

	/**
	 * @param player The player we are setting the visibility of this object to
	 * @param visibility true or false to set the players visibility to that;
	 * null to reset it
	 */
	public void setPhysicalVisibility(Player player, Boolean visibility)
	{
		if(visibility == null)
			this.physicalVisibility.remove(player.ID);
		else
			this.physicalVisibility.put(player.ID, visibility);
	}

	public void setPower(int power)
	{
		this.characteristics[0].power = power;
	}

	public void setPrintedLoyalty(int loyalty)
	{
		this.characteristics[0].loyalty = loyalty;
	}

	public void setSelectedModeNumbers(java.util.List<Integer>[] selectedModeNumbers)
	{
		for(int i = 0; i < this.characteristics.length; ++i)
			this.characteristics[i].selectedModeNumbers = selectedModeNumbers[i];
	}

	public void setTapped(boolean tapped)
	{
		this.battlefieldProperties.tapped = tapped;
	}

	public void setToughness(int toughness)
	{
		this.characteristics[0].toughness = toughness;
	}

	public void setTransformed(boolean transformed)
	{
		this.battlefieldProperties.transformed = transformed;
	}

	public void setValueOfX(int valueOfX)
	{
		for(Characteristics characteristics: this.characteristics)
			characteristics.valueOfX = valueOfX;
	}

	/**
	 * Tells this object it has moved.
	 *
	 * @param zone The zone it moved to.
	 */
	public void setZone(Zone zone)
	{
		if(zone == null)
			throw new UnsupportedOperationException("Attempt to set zone of GameObject to null");
		this.zoneID = zone.ID;
	}

	/**
	 * Splices this object onto the specified object.
	 *
	 * @param target The object whose text box will receive a copy of this
	 * object's text box.
	 */
	public void spliceOnto(GameObject target)
	{
		for(Mode mode: this.getModes()[0])
		{
			Mode newMode = new Mode(mode, target.ID);
			target.getModes()[0].add(newMode);
		}

		Set grantedByKeywords = new Set();
		for(Keyword k: this.getKeywordAbilities())
			grantedByKeywords.addAll(k.getAbilitiesGranted());

		for(int abilityID: this.getAbilityIDsInOrder())
		{
			if(abilityID == -1)
				continue;

			Identified ability = this.game.actualState.get(abilityID);
			if(grantedByKeywords.contains(ability))
				continue;

			if(ability.isActivatedAbility() || ability.isTriggeredAbility())
			{
				NonStaticAbility newAbility = (NonStaticAbility)((NonStaticAbility)ability).create(this.game);
				target.addAbility(newAbility);
			}
			else if(ability.isKeyword())
			{
				Keyword newAbility = ((Keyword)ability).create(this.game);
				target.addAbility(newAbility);
			}
			// it's a static ability
			else
			{
				StaticAbility newAbility = ((StaticAbility)ability).create(this.game);
				target.addAbility(newAbility);
			}
		}
	}

	public void applyCopiableValues(GameState state, CopiableValues cvs)
	{
		if(cvs.toCopy.contains(Characteristics.Characteristic.RULES_TEXT))
		{
			this.removeAllAbilities();
			this.setBottomHalf(cvs.bottomHalf);
		}

		if(this.characteristics.length < cvs.characteristics.length)
			this.characteristics = java.util.Arrays.copyOf(this.characteristics, cvs.characteristics.length);

		// If this object is flipped, then the name will be set by the
		// Characteristics object for the bottom half, in the next block
		if(cvs.bottomHalf == null || !this.isFlipped())
			this.setName(java.util.Arrays.stream(cvs.characteristics).map(t -> t.name).reduce((left, right) -> left + " // " + right).orElse(""));

		for(int i = 0; i < cvs.characteristics.length; ++i)
		{
			Characteristics toApply = cvs.characteristics[i];

			if(this.characteristics[i] == null)
			{
				this.characteristics[i] = toApply.create(this);
			}
			else
			{
				if(cvs.bottomHalf != null && this.isFlipped())
				{
					toApply = cvs.bottomHalf;
					this.setName(toApply.name);
				}

				if(cvs.toCopy.contains(Characteristics.Characteristic.NAME))
					this.characteristics[i].name = toApply.name;

				if(cvs.toCopy.contains(Characteristics.Characteristic.POWER))
					this.characteristics[i].power = toApply.power;

				if(cvs.toCopy.contains(Characteristics.Characteristic.TOUGHNESS))
					this.characteristics[i].toughness = toApply.toughness;

				if(cvs.toCopy.contains(Characteristics.Characteristic.LOYALTY))
					this.characteristics[i].loyalty = toApply.loyalty;

				if(cvs.toCopy.contains(Characteristics.Characteristic.MANA_COST))
				{
					if(toApply.manaCost != null)
						this.characteristics[i].manaCost = new ManaPool(toApply.manaCost);
					else
						this.characteristics[i].manaCost = null;
				}

				if(cvs.toCopy.contains(Characteristics.Characteristic.RULES_TEXT))
				{
					this.characteristics[i].minimumX = toApply.minimumX;

					{
						for(Integer abilityID: toApply.nonStaticAbilities)
							this.addAbility(state.<NonStaticAbility>get(abilityID));
						for(Integer abilityID: toApply.staticAbilities)
							this.addAbility(state.<StaticAbility>get(abilityID));

						// Add keywords without applying them, since the
						// abilities
						// they
						// would grant have already been taken care of.
						for(Integer abilityID: toApply.keywordAbilities)
							this.addAbility(state.<Keyword>get(abilityID), false);

						this.characteristics[i].abilityIDsInOrder = new java.util.LinkedList<Integer>(toApply.abilityIDsInOrder);
					}

					this.characteristics[i].costs.clear();
					{
						for(EventFactory cost: toApply.costs)
							this.characteristics[i].costs.add(cost);
					}

					this.characteristics[i].modes.clear();
					{
						for(Mode mode: toApply.modes)
							this.characteristics[i].modes.add(mode);
					}

				}

				if(cvs.toCopy.contains(Characteristics.Characteristic.COLOR))
				{
					this.characteristics[i].colors.clear();
					this.characteristics[i].colors.addAll(toApply.colors);
					this.characteristics[i].colorIndicator.clear();
					this.characteristics[i].colorIndicator.addAll(toApply.colorIndicator);
				}

				if(cvs.toCopy.contains(Characteristics.Characteristic.TYPES))
				{
					this.characteristics[i].superTypes.clear();
					this.characteristics[i].superTypes.addAll(toApply.superTypes);

					this.characteristics[i].types.clear();
					this.characteristics[i].types.addAll(toApply.types);

					this.characteristics[i].subTypes.clear();
					this.characteristics[i].subTypes.addAll(toApply.subTypes);
				}

				if(cvs.originalWasOnStack)
				{
					if(cvs.toCopy.contains(Characteristics.Characteristic.CHOICES_MADE_WHEN_PLAYING))
					{
						this.characteristics[i].alternateCost = toApply.alternateCost;

						this.characteristics[i].optionalAdditionalCostsChosen.clear();
						this.characteristics[i].optionalAdditionalCostsChosen.addAll(toApply.optionalAdditionalCostsChosen);

						this.characteristics[i].selectedModeNumbers.clear();
						this.characteristics[i].selectedModeNumbers.addAll(toApply.selectedModeNumbers);

						this.characteristics[i].valueOfX = toApply.valueOfX;

						this.characteristics[i].chosenTargets.clear();
						this.characteristics[i].chosenTargets.putAll(toApply.chosenTargets);
					}

					if(toApply.sourceID != -1 && (this.isActivatedAbility() || this.isTriggeredAbility()))
						((NonStaticAbility)this).sourceID = toApply.sourceID;
				}
			}
		}
	}
}
