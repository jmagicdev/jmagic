package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.eventTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/**
 * Before adding a new EventType to this file, please see the note near the top
 * about the attempt() method.
 */

public abstract class EventType
{
	public enum Parameter
	{
		ABILITY, ACTION, ALTERNATE_COST, ATTACKER, CARD, CAUSE, CHOICE, COLOR, CONTROLLER, COST, COUNTER, DAMAGE, DEFENDER, EFFECT, ELSE, EVENT, EXPIRES, FACE_DOWN, FROM, HIDDEN, IF, INDEX, LAND, MANA, MULTIPLY, NAME, NUMBER, OBJECT, ORDERED, PERMANENT, PHASE, PLAYER, POWER, PREVENT, RANDOM, REASON, RESOLVING, SOURCE, STEP, SUBTYPE, SUPERTYPE, TAKER, TAPPED, TARGET, THEN, TO, TOUGHNESS, TURN, TYPE, USES, ZONE, ZONE_CHANGE
	}

	public static class ParameterMap extends java.util.HashMap<Parameter, SetGenerator>
	{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * Any EventType calling this should read the documentation on
	 * {@link EventType#addsMana()}.
	 * 
	 * @eparam SOURCE: what is producing the mana
	 * @eparam MANA: mana being added, or colors of mana to add
	 * @eparam MULTIPLY: the number of times to duplicate the mana in the pool
	 * before exploding [optional; default is 1]
	 * @eparam NUMBER: the number of times to add the exploded mana into the
	 * players pool [optional; default is 1]
	 * @eparam PLAYER: who is getting the mana
	 * @eparam RESULT: the mana objects as they exist in the player(s) manapool
	 */
	public static final EventType ADD_MANA = AddMana.INSTANCE;

	/**
	 * @eparam PLAYER: the player getting the counters
	 * @eparam NUMBER: the number to add
	 * @eparam RESULT: the counters as they exist on the player
	 */
	public static final EventType ADD_POISON_COUNTERS = AddPoisonCounters.INSTANCE;

	/**
	 * Causes a single creature's combat damage to be assigned. This event type
	 * determines who will assign the damage, as well as how much damage to
	 * assign (thanks, Illusionary Mask).
	 * 
	 * @eparam OBJECT: the creature assigning damage
	 * @eparam TARGET: a {@link java.util.List} of {@link Target}s, where each
	 * target points at a damage-receival candidate. Upon completion of this
	 * event, this list's targets will have their {@link Target#division} fields
	 * set to the player's assignments.
	 * @eparam NUMBER: the amount of damage to assign, if that amount would be
	 * different from the amount the creature would normally assign (for
	 * example, if there is an effect telling the creature to assign no combat
	 * damage this turn). don't set this parameter at all if the creature is to
	 * assign its normal amount of combat damage.
	 * @eparam RESULT: empty
	 */
	public static final EventType ASSIGN_COMBAT_DAMAGE = AssignCombatDamage.INSTANCE;

	/**
	 * @eparam OBJECT: the GameObject instances attaching by
	 * @eparam TARGET: the AttachableTo which will become attached to
	 * @eparam RESOLVING: if this parameter is present, it signifies that this
	 * attachment event is part of an Aura spell resolving. (card writers, this
	 * isn't for you!)
	 * @eparam RESULT: the AttachableBy instances which are now attached to
	 */
	public static final EventType ATTACH = Attach.INSTANCE;

	/**
	 * @eparam OBJECT: the {@link GameObject} which will be attached (must be a
	 * single {@link GameObject})
	 * @eparam PLAYER: the player that is attaching (and thus choosing)
	 * @eparam CHOICE: the AttachableTo which will become chosen from to attach
	 * to
	 * @eparam RESULT: the GameObject instances which are now attached to
	 */
	public static final EventType ATTACH_TO_CHOICE = AttachToChoice.INSTANCE;

	/**
	 * @eparam ATTACKER: the blocked attacker
	 * @eparam DEFENDER: the blockers
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_BLOCKED = BecomesBlocked.INSTANCE;

	/**
	 * @eparam ATTACKER: the blocked attacker
	 * @eparam DEFENDER: a single object blocking the attacker
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_BLOCKED_BY_ONE = BecomesBlockedByOne.INSTANCE;

	/**
	 * Marker event for a permanent becoming monstrous.
	 * 
	 * @eparam OBJECT: the permanent
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_MONSTROUS = BecomesMonstrous.INSTANCE;

	/**
	 * This is a marker event for the moment at which a spell or ability becomes
	 * played. Mana abilities resolve here.
	 * 
	 * 601.2h Once the steps described in 601.2a-g are completed, the spell
	 * becomes cast. Any abilities that trigger when a spell is cast or put onto
	 * the stack trigger at this time.
	 * 
	 * @eparam PLAYER: for spells, the player who cast the spell; for activated
	 * abilities, the player who activated the ability; for triggered abilities,
	 * not present; for lands, the player playing the land
	 * @eparam OBJECT: the spell/ability/land
	 * @eparam RESULT: the first GameObject in OBJECT
	 */
	public static final EventType BECOMES_PLAYED = BecomesPlayed.INSTANCE;

	/**
	 * @eparam OBJECT: the targetter
	 * @eparam TARGET: the targettee
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_TARGET = BecomesTarget.INSTANCE;

	/**
	 * @eparam ATTACKER: the unblocked attacker
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_UNBLOCKED = BecomesUnblocked.INSTANCE;

	/**
	 * @eparam PHASE: the phase
	 * @eparam RESULT: the phase
	 */
	public static final EventType BEGIN_PHASE = BeginPhase.INSTANCE;

	/**
	 * @eparam STEP: the step
	 * @eparam RESULT: the step
	 */
	public static final EventType BEGIN_STEP = BeginStep.INSTANCE;

	/**
	 * @eparam TURN: the turn
	 * @eparam RESULT: the turn
	 */
	public static final EventType BEGIN_TURN = BeginTurn.INSTANCE;

	/**
	 * Don't use this EventType directly. It should always be wrapped within an
	 * action so that any failure in casting the spell or ability will
	 * automatically be reverted.
	 * 
	 * If you are a card writer and you are tempted to use this EventType on
	 * your card, you should be using PLAYER_MAY_CAST or PLAY_CARD instead, as
	 * appropriate (depending on whether the card in question could be a land
	 * card).
	 * 
	 * If you are a card writer, and you are writing a "can't cast" effect, and
	 * that effect depends on the object's characteristics, you should use a
	 * PlayProhibition rather than prohibiting this event type. If that effect
	 * doesn't depend on the object's characteristics, prohibit this event type
	 * directly.
	 * 
	 * Exactly one of EFFECT and FACE_DOWN must be passed. If both or neither is
	 * passed, it will result in unexpected behavior.
	 * 
	 * @eparam PLAYER: the player playing the spell/ability
	 * @eparam ALTERNATE_COST: a set of forced alternate costs represented as
	 * events and/or mana pools [optional; default = no forced alternate cost]
	 * @eparam EFFECT: which side(s) of a split card may be cast, as 0-based
	 * indices.
	 * @eparam OBJECT: the spell/ability
	 * @eparam ACTION: if the object being played is a spell being cast using an
	 * action, that action; otherwise leave it out
	 * @eparam FACE_DOWN: if OBJECT is a spell and is to be cast face down, a
	 * class extending CopiableValues defining the characteristics the face-down
	 * spell is to have (see {@link ZoneChange#faceDownCharacteristics})
	 * @eparam RESULT: the object as it exists on the stack, empty otherwise
	 */
	public static final EventType CAST_SPELL_OR_ACTIVATE_ABILITY = CastSpellOrActivateAbility.INSTANCE;

	/**
	 * This is a marker event. Don't call it.
	 * 
	 * @eparam OBJECT: the object
	 * @eparam PLAYER: the old controller (optional; default = no one)
	 * @eparam TARGET: the new controller (optional; default = no one)
	 * @eparam ATTACKER: optional parameter; if present, the objects will not be
	 * removed from combat (intended only for use by
	 * {@link EventType#MOVE_BATCH})
	 * @eparam RESULT: empty
	 */
	public static final EventType CHANGE_CONTROL = ChangeControl.INSTANCE;

	/**
	 * @eparam OBJECT: the spell or ability to change a target of
	 * @eparam TARGET: the new assignment for the target
	 * @eparam PLAYER: the player choosing which target to reassign
	 * @eparam RESULT: the objects whose targets were changed
	 */
	public static final EventType CHANGE_SINGLE_TARGET_TO = ChangeSingleTargetTo.INSTANCE;

	/**
	 * @eparam OBJECT: the spell or ability to choose new targets for
	 * @eparam PLAYER: the player choosing the targets
	 * @eparam RESULT: the objects whose targets were changed
	 */
	public static final EventType CHANGE_TARGETS = ChangeTargets.INSTANCE;

	/**
	 * @eparam PLAYER: who is choosing
	 * @eparam EVENT: event factories for the events to choose from
	 * @eparam RESULT: the chosen event
	 */
	public static final EventType CHOOSE_AND_PERFORM = ChooseAndPerform.INSTANCE;

	/**
	 * @eparam CAUSE: the cause
	 * @eparam PLAYER: the players involved in the clash
	 * @eparam RESULT: the winner of the clash
	 */
	public static final EventType CLASH = Clash.INSTANCE;

	/**
	 * @eparam CAUSE: the cause
	 * @eparam PLAYER: the player initiating the clash
	 * @eparam RESULT: the winner of the clash
	 */
	public static final EventType CLASH_WITH_AN_OPPONENT = ClashWithAnOpponent.INSTANCE;

	/**
	 * This is the event you want to use when an effect has a player copy an
	 * exiled card and 'cast the copy', rather than copying a spell on the
	 * stack.
	 * 
	 * @eparam CAUSE: what is copying the card
	 * @eparam OBJECT: what is being copied (singular)
	 * @eparam NUMBER: how many copies to make [optional; default 1]
	 * @eparam RESULT: the copies created
	 */
	public static final EventType COPY_SPELL_CARD = CopySpellCard.INSTANCE;

	/**
	 * @eparam CAUSE: what is copying the spell
	 * @eparam OBJECT: the spell to copy for each possible target
	 * @eparam TARGET: a subset of the targets to limit it to
	 * @eparam RESULT: empty
	 */
	public static final EventType COPY_SPELL_FOR_EACH_TARGET = CopySpellForEachTarget.INSTANCE;

	/**
	 * @eparam CAUSE: what is copying the spell
	 * @eparam OBJECT: the spell to copy
	 * @eparam PLAYER: the player who will control the spell (and choose the new
	 * targets if applicable) (don't specify this if the spell is in the exile
	 * zone)
	 * @eparam TARGET: if this parameter is present, the choice to choose new
	 * targets will not be given
	 * @eparam NUMBER: the number of times to copy the spell [optional; default
	 * is 1]
	 * @eparam RESULT: the copy
	 */
	public static final EventType COPY_SPELL_OR_ABILITY = CopySpellOrAbility.INSTANCE;

	/**
	 * @eparam CAUSE: what is countering
	 * @eparam OBJECT: objects being countered
	 * @eparam TO: zone to put the countered object [optional; default is
	 * owner's graveyard]
	 * @eparam RESULT: the objects as they exist after the counter
	 */
	public static final EventType COUNTER = CounterSpellOrAbility.INSTANCE;

	/**
	 * @eparam CAUSE: what is countering
	 * @eparam OBJECT: object being countered
	 * @eparam TO: zone to put the countered object [optional; default is
	 * owner's graveyard]
	 * @eparam RESULT: the object as it exists after the counter
	 */

	public static final EventType COUNTER_ONE = CounterOne.INSTANCE;

	/**
	 * <p>
	 * 603.7d If a spell creates a delayed triggered ability, the source of that
	 * delayed triggered ability is that spell. The controller of that delayed
	 * triggered ability is the player who controlled that spell as it resolved.
	 * </p>
	 * <p>
	 * 603.7e If an activated or triggered ability creates a delayed triggered
	 * ability, the source of that delayed triggered ability is the same as the
	 * source of that other ability. The controller of that delayed triggered
	 * ability is the player who controlled that other ability as it resolved.
	 * </p>
	 * <p>
	 * 603.7f If a static ability generates a replacement effect which causes a
	 * delayed triggered ability to be created, the source of that delayed
	 * triggered ability is the object with that static ability. The controller
	 * of that delayed triggered ability is the same as the controller of that
	 * object at the time the replacement effect was applied.
	 * </p>
	 * 
	 * @eparam CAUSE: the object creating the trigger, as defined above, EXCEPT
	 * that if an activated or triggered ability is creating this delayed
	 * trigger, this parameter should be that ability rather than that ability's
	 * source (so that this event can properly set the controller of the delayed
	 * trigger). Long Story Short: You're usually passing "This.instance()"
	 * here, but if you're a replacement effect it's possible you'll need to be
	 * more careful.
	 * @eparam EVENT: event patterns that describe what the new trigger will
	 * trigger on [can't be specified with ZONE_CHANGE or DAMAGE]
	 * @eparam DAMAGE: damage patterns that describe what the new trigger will
	 * trigger on [can't be specified with EVENT or ZONE_CHANGE]
	 * @eparam ZONE_CHANGE: ZoneChangePattern instances that describe what the
	 * new trigger will trigger on [can't be specified with EVENT or DAMAGE]
	 * @eparam EFFECT: an EventFactory to construct the trigger's effect
	 * (remember that any use of This will refer to the created ability, not to
	 * the creating {@link GameObject})
	 * @eparam EXPIRES: when the delayed trigger should expire [optional;
	 * default is until the end of the game or when it triggers once, whichever
	 * is shorter; requires double-generator idiom]
	 * @eparam COST: a CostCollection that PLAYER can pay to stop the delayed
	 * trigger from triggering [optional; requires PLAYER]
	 * @eparam PLAYER: who can stop the delayed trigger from triggering by
	 * paying COST [optional; requires COST]
	 * @eparam RESULT: the trigger
	 */
	public static final EventType CREATE_DELAYED_TRIGGER = CreateDelayedTrigger.INSTANCE;

	/**
	 * @eparam CAUSE: what is creating the emblem
	 * @eparam ABILITY: any abilities the emblem should have [optional; default
	 * is none]
	 * @eparam CONTROLLER: who will control the emblem
	 * @eparam RESULT: the emblem as it exists in the battlefield
	 */
	public static final EventType CREATE_EMBLEM = CreateEmblem.INSTANCE;

	/**
	 * @eparam CAUSE: what is creating the continuous effect(s)
	 * @eparam EFFECT: optionally, any parts of a continuous effect to use to
	 * create a new floating continuous effect (note that using the PREVENT
	 * parameter will automatically add a part)
	 * @eparam EXPIRES: optionally, when the floating continuous effect will
	 * expire; requires double-generator idiom (default is until end of turn)
	 * @eparam PREVENT: optionally, an amount of damage and a
	 * {@link SetGenerator} of {@link GameObject} and/or {@link Player} to
	 * prevent damage to; requires double-generator idiom (default is none)
	 * @eparam USES: optionally, the number of uses the floating continuous
	 * effect is limited to (default is unlimited)
	 * @eparam DAMAGE: optionally, if EFFECT is specified and the effect
	 * prevents or redirects damage, how much damage this effect can prevent or
	 * redirect
	 * @eparam RESULT: all floating continuous effects in EFFECT
	 */
	public static final EventType CREATE_FLOATING_CONTINUOUS_EFFECT = CreateFloatingContinuousEffect.INSTANCE;

	/**
	 * @eparam CAUSE: what's creating the shield
	 * @eparam OBJECT: the objects being shielded
	 * @eparam RESULT: the regeneration shields
	 */
	public static final EventType CREATE_REGENERATION_SHIELD = CreateRegenerationShield.INSTANCE;

	/**
	 * @eparam NUMBER: the number of tokens to instantiate
	 * @eparam NAME: the name of the tokens
	 * @eparam ABILITY: the abilities
	 * @eparam CONTROLLER: the controller of the tokens (not used, but important
	 * to be set for effects like Doubling Season to see it)
	 * @eparam RESULT: the tokens
	 */
	public static final EventType CREATE_TOKEN = CreateToken.INSTANCE;

	/**
	 * @eparam CAUSE: what is creating the tokens
	 * @eparam ABILITY: any abilities the token should have [optional; default
	 * is none]
	 * @eparam COLOR: any colors the token should have [optional; default is
	 * none]
	 * @eparam CONTROLLER: who will control the tokens
	 * @eparam NAME: the name of the token [optional; default is to construct
	 * the name according to rule 110.5c]
	 * @eparam NUMBER: the number of times to create these tokens [optional;
	 * default is once]
	 * @eparam POWER: the power of the token, if it's a creature [required if
	 * CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam SUBTYPE: the sub-types of the token (this must evaluate to a
	 * java.util.List<SubType> rather than the SubType instances themselves so
	 * order is maintained) [required if NAME is not specified; otherwise,
	 * optional; default is none]
	 * @eparam SUPERTYPE: the super-types of the token [optional; default is
	 * none]
	 * @eparam TOUGHNESS: the toughness of the token, if it's a creature
	 * [required if CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam TYPE: the types of the token (if CREATURE is included, POWER and
	 * TOUGHNESS are required)
	 * @eparam EVENT: the EventType by which to put the token on the battlefield
	 * [optional; default is PUT_ONTO_BATTLEFIELD.
	 * PUT_ONTO_BATTLEFIELD_ATTACKING, PUT_ONTO_BATTLEFIELD_TAPPED,
	 * PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING and
	 * PUT_ONTO_BATTLEFIELD_BLOCKING are also acceptable]
	 * @eparam ATTACKER: if EVENT is something that puts the token onto the
	 * battlefield attacking, the attackingID to pass to that event [optional,
	 * default is none; prohibited when EVENT is something else]
	 * @eparam RESULT: the tokens as they exist on the battlefield
	 */
	public static final EventType CREATE_TOKEN_ON_BATTLEFIELD = CreateTokenOnBattlefield.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam ATTACKER: the attacker to block
	 * @eparam ABILITY: any abilities the token should have [optional; default
	 * is none]
	 * @eparam COLOR: any colors the token should have [optional; default is
	 * none]
	 * @eparam CONTROLLER: who will control the tokens
	 * @eparam NAME: the name of the token [optional; default is to construct
	 * the name according to rule 110.5c]
	 * @eparam NUMBER: the number of times to create these tokens [optional;
	 * default is once]
	 * @eparam POWER: the power of the token, if it's a creature [required if
	 * CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam SUBTYPE: the sub-types of the token (this must evaluate to a
	 * java.util.List<SubType> rather than the SubType instances themselves so
	 * order is maintained) [required if NAME is not specified; otherwise,
	 * optional; default is none]
	 * @eparam SUPERTYPE: the super-types of the token [optional; default is
	 * none]
	 * @eparam TOUGHNESS: the toughness of the token, if it's a creature
	 * [required if CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam TYPE: the types of the token (if CREATURE is included, POWER and
	 * TOUGHNESS are required)
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType CREATE_TOKEN_BLOCKING = CreateTokenBlocking.INSTANCE;

	/**
	 * @eparam CAUSE: what is creating the tokens
	 * @eparam CONTROLLER: who will control the tokens
	 * @eparam NUMBER: the number of times to create these tokens [optional;
	 * default is once]
	 * @eparam OBJECT: the object to make token copies of (singular, if this
	 * parameter is empty then no tokens will be made and the event will fail)
	 * @eparam TYPE: Any {@link Type}s, {@link SuperType}s, or {@link SubType}s
	 * to add as part of the copying process
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType CREATE_TOKEN_COPY = CreateTokenCopy.INSTANCE;

	/**
	 * @eparam TARGET: DamageAssignment objects
	 * @eparam RESULT: empty
	 */
	public static final EventType DEAL_COMBAT_DAMAGE = DealCombatDamage.INSTANCE;

	/**
	 * This event ACTUALLY DEALS DAMAGE. This event should only be created or
	 * performed in Event.perform().
	 * 
	 * @eparam TARGET: DamageAssignments
	 * @eparam RESULT: the damage assignments of the damage actually dealt
	 */
	public static final EventType DEAL_DAMAGE_BATCHES = DealDamageBatches.INSTANCE;

	/**
	 * @eparam SOURCE: what is dealing damage
	 * @eparam NUMBER: how much damage
	 * @eparam TAKER: what is being damaged
	 * @eparam PREVENT: [optional] if present, the damage is unpreventable.
	 * @eparam RESULT: empty
	 */
	public static final EventType DEAL_DAMAGE_EVENLY = DealDamageEvenly.INSTANCE;

	/**
	 * @eparam SOURCE: what is dealing damage
	 * @eparam NUMBER: how much damage
	 * @eparam TAKER: what is being damaged
	 * @eparam PREVENT: [optional] if present, the damage is unpreventable
	 * @eparam RESULT: empty
	 */
	public static final EventType DEAL_DAMAGE_EVENLY_CANT_BE_REGENERATED = DealDamageEvenlyCantBeRegenerated.INSTANCE;

	/**
	 * @eparam RESULT: the creatures declared as attacking
	 */
	public static final EventType DECLARE_ATTACKERS = DeclareAttackers.INSTANCE;

	/**
	 * @eparam RESULT: empty
	 */
	public static final EventType DECLARE_BLOCKERS = DeclareBlockers.INSTANCE;

	/**
	 * @eparam OBJECT: the attacking creature
	 * @eparam DEFENDER: the thing it's attacking
	 * @eparam RESULT: empty
	 */
	public static final EventType DECLARE_ONE_ATTACKER = DeclareOneAttacker.INSTANCE;

	/**
	 * @eparam OBJECT: the blocking creature
	 * @eparam ATTACKER: the things it's blocking
	 * @eparam RESULT: empty
	 */
	public static final EventType DECLARE_ONE_BLOCKER = DeclareOneBlocker.INSTANCE;

	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is destroying something
	 * @eparam PERMANENT: the permanent being destroyed
	 * @eparam RESULT: the result of the {@link EventType#MOVE_OBJECTS} event
	 */
	public static final EventType DESTROY_ONE_PERMANENT = DestroyOnePermanent.INSTANCE;

	/**
	 * @eparam CAUSE: what is destroying things
	 * @eparam PERMANENT: what is being destroyed
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType DESTROY_PERMANENTS = DestroyPermanents.INSTANCE;

	/**
	 * 701.26. Detain
	 * 
	 * 701.26a Certain spells and abilities can detain a permanent. Until the
	 * next turn of the controller of that spell or ability, that permanent
	 * can't attack or block and its activated abilities can't be activated.
	 * 
	 * NOTE: Make sure to call
	 * GameState.ensureTracker(DetainGenerator.Tracker.class).
	 * 
	 * @eparam CAUSE: the spell or ability causing the detainment
	 * @eparam PERMANENT: the permanents to detain
	 * @eparam RESULT: empty
	 */
	public static final EventType DETAIN = Detain.INSTANCE;

	/**
	 * Causes a player to discard a specific set of cards from their hand. To
	 * cause a player to discard cards of their choice, use DISCARD_CHOICE. To
	 * have another player choose which cards the player discards, use
	 * DISCARD_FORCE.
	 * 
	 * @eparam CAUSE: what is causing the discard
	 * @eparam CARD: what is being discarded
	 * @eparam RESULT: the {@link ZoneChange}s that were generated
	 */
	public static final EventType DISCARD_CARDS = DiscardCards.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the players that are discarding (and thus choosing)
	 * @eparam NUMBER: number of cards to discard [optional; default is 1]
	 * @eparam CHOICE: set of cards to choose from [optional; default is all the
	 * cards in the players' hands] NOTE: If Parameter.CHOICE is not left as
	 * default, it's a Bad Thing(tm) to have multiple players specified here.
	 * @eparam RESULT: the zone change(s)
	 */
	public static final EventType DISCARD_CHOICE = DiscardChoice.INSTANCE;

	/**
	 * This event causes PLAYER to look at all of the cards in TARGET's hand
	 * since it invokes a {@link EventType#SEARCH}.
	 * 
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the player that is choosing (just one)
	 * @eparam TARGET: the player that is discarding (just one)
	 * @eparam NUMBER: number of cards to discard [optional; default is 1]
	 * @eparam CHOICE: kind of cards to choose from [optional; default is all
	 * the cards in the player's hand] (uses double-generator idiom)
	 * @eparam RESULT: the results of the DISCARD_CARDS events
	 */
	public static final EventType DISCARD_FORCE = DiscardForce.INSTANCE;

	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is causing the discard
	 * @eparam CARD: what is being discarded
	 * @eparam TO: where the card will go [optional; default = owner's
	 * graveyard]
	 * @eparam CONTROLLER: who will control the card after it's discarded
	 * [required if TO is the battlefield or the stack; prohibited otherwise]
	 * @eparam RESULT: the zone change
	 */
	public static final EventType DISCARD_ONE_CARD = DiscardOneCard.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the players that are discarding
	 * @eparam NUMBER: number of cards to discard
	 * @eparam CARD: set of cards to choose from [optional - default is the
	 * players' hands]
	 * @eparam RESULT: the results of the {@link #DISCARD_CARDS} event(s)
	 */
	public static final EventType DISCARD_RANDOM = DiscardRandom.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the players discarding
	 * @eparam NUMBER: number of cards for each player to keep
	 * @eparam RESULT: the results of the DISCARD_CARDS event(s)
	 */
	public static final EventType DISCARD_TO = DiscardTo.INSTANCE;

	/**
	 * @eparam CAUSE: the cause of the counters
	 * @eparam PLAYER: the player distributing the counters
	 * @eparam OBJECT: targets describing who is getting counters and how many
	 * @eparam COUNTER: the Counter.CounterType of counters to distribute
	 * @eparam RESULT: empty
	 */
	public static final EventType DISTRIBUTE_COUNTERS = DistributeCounters.INSTANCE;

	/**
	 * @eparam SOURCE: the source of damage
	 * @eparam TAKER: targets describing who is taking damage and how much
	 * @eparam PREVENT: [optional] if present, the damage is unpreventable
	 * @eparam RESULT: empty
	 */
	public static final EventType DISTRIBUTE_DAMAGE = DistributeDamage.INSTANCE;

	/**
	 * @eparam SOURCE: the source of the new mana
	 * @eparam PLAYER: the player whose mana to double
	 * @eparam MANA: the mana to double
	 * @eparam RESULT: the mana that was created this way
	 */
	public static final EventType DOUBLE_MANA = DoubleMana.INSTANCE;

	/**
	 * @eparam EVENT: An EventFactory that will construct the appropriate draw
	 * event. If this draw-and-reveal effect is part of a replacement effect,
	 * this should be an EventParts of the replacement effect's
	 * "replacedByThis()" generator. Otherwise, construct a normal draw event
	 * factory and pass it to this parameter.
	 * @eparam RESULT: The result of the draw event.
	 */
	public static final EventType DRAW_AND_REVEAL = DrawAndReveal.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the draw
	 * @eparam PLAYER: the players that are drawing
	 * @eparam NUMBER: number of cards to draw
	 * @eparam RESULT: the results of the {@link #DRAW_ONE_CARD} event(s)
	 */
	public static final EventType DRAW_CARDS = DrawCards.INSTANCE;

	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is causing the draw
	 * @eparam PLAYER: the player that is drawing
	 * @eparam RESULT: the result of the {@link #MOVE_OBJECTS} event
	 */
	public static final EventType DRAW_ONE_CARD = DrawOneCard.INSTANCE;

	/** @eparam RESULT: Empty */
	public static final EventType EMPTY_ALL_MANA_POOLS = EmptyAllManaPools.INSTANCE;

	/**
	 * @eparam CAUSE: what is emptying the mana pools
	 * @eparam PLAYER: whose mana pools to empty
	 * @eparam RESULT: the mana emptied
	 */
	public static final EventType EMPTY_MANA_POOL = EmptyManaPool.INSTANCE;

	/**
	 * @eparam STEP: the step that is ending
	 * @eparam RESULT: empty
	 */
	public static final EventType END_STEP = EndStep.INSTANCE;

	/**
	 * @eparam CAUSE: why is the turn ending?
	 * @eparam RESULT: nothing. thats all thats left. nothing.
	 */
	public static final EventType END_THE_TURN = EndTheTurn.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the swap
	 * @eparam OBJECT: the two objects whose control is beings swapped (no more
	 * than two, no less than two, the number of the counting shall be two, not
	 * one, not three, four is right out)
	 * @eparam RESULT: empty
	 */
	public static final EventType EXCHANGE_CONTROL = ExchangeControl.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the swap
	 * @eparam PLAYER: the two players swapping lifetotals (no more than two, no
	 * less than two)
	 * @eparam RESULT: empty
	 */
	public static final EventType EXCHANGE_LIFE_TOTALS = ExchangeLifeTotals.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the choice
	 * @eparam NUMBER: how many to choose (integer or range) [optional; default
	 * is 1]
	 * @eparam OBJECT: what is being chosen from
	 * @eparam PLAYER: who is choosing (singular)
	 * @eparam HIDDEN: if present, the cards should be exiled "face down"
	 * (hidden).
	 * @eparam RESULT: results of the MOVE_OBJECTS event
	 */
	public static final EventType EXILE_CHOICE = ExileChoice.INSTANCE;

	/**
	 * Exiles a permanent from the battlefield, returning it when a specified
	 * event occurs. The way this actually works is that it looks at a
	 * generator, and returns the object when that generator is nonempty. The
	 * generator shouldn't simply refer to (e.g.) the Banisher Priest not being
	 * on the battlefield, but rather should refer to a tracker that explicitly
	 * tracks objects (e.g.) leaving the battlefield. The rules are pretty
	 * explicit that the return happens after a specified *event*, not under a
	 * certain *condition*, but trackers are our only way of looking for events.
	 * 
	 * @eparam CAUSE: what is causing the exile
	 * @eparam OBJECT: what is being exiled
	 * @eparam HIDDEN: if present, the cards should be exiled "face down"
	 * (hidden).
	 * @eparam EXPIRES: a setgenerator describing when to return the object
	 * (object returns when this generator is nonempty). This must be a
	 * SetGenerator (use the double-generator idiom).
	 * @eparam RESULT: results of the MOVE_OBJECTS event
	 */
	public static final EventType EXILE_UNTIL = ExileUntil.INSTANCE;

	/**
	 * IMPORTANT NOTE: The OBJECT parameter takes two objects that can either be
	 * 2 Targets, 1 Target and 1 GameObject, or 2 GameObjects. If there is a
	 * possibility that both objects could represent the same creature, they are
	 * not allowed to both be represented by GameObjects. Otherwise, Set
	 * uniqueness will remove one, and performing the event will fail.
	 * 
	 * 701.10. Fight
	 * 
	 * 701.10a A spell or ability may instruct a creature to fight another
	 * creature. To fight, each creature deals damage equal to its power to the
	 * other creature. When such a spell or ability resolves, if either creature
	 * is no longer on the battlefield, is no longer a creature, or is otherwise
	 * an illegal target (if the spell or ability targeted that creature), no
	 * damage is dealt.
	 * 
	 * 701.10b If a creature fights itself, it deals damage equal to its power
	 * to itself twice.
	 * 
	 * 701.10c The damage dealt when a creature fights isn't combat damage.
	 * 
	 * @eparam CAUSE: the cause of the fighting
	 * @eparam OBJECT: two objects representing the creatures that are fighting
	 * @eparam RESULT: the creatures that fought
	 */
	public static final EventType FIGHT = Fight.INSTANCE;

	/**
	 * @eparam OBJECT: the card to flip
	 * @eparam RESULT: empty
	 */
	public static final EventType FLIP_CARD = FlipCard.INSTANCE;

	/**
	 * @eparam PLAYER: who is flipping
	 * @eparam TYPE: a set containing exactly two Answer objects representing
	 * the possible results of this coin flip (optional; default is WIN/LOSE; if
	 * you use this parameter you'll probably use HEADS/TAILS (TODO : implement
	 * HEADS/TAILS flips))
	 * @eparam RESULT: an Answer that is the result of this flip
	 */
	public static final EventType FLIP_COIN = FlipCoin.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the life gain
	 * @eparam PLAYER: the players gaining the life
	 * @eparam NUMBER: how much life is being gained
	 * @eparam RESULT: the players that gained life and each amount of life
	 * gained by a player
	 */
	public static final EventType GAIN_LIFE = GainLife.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the life gain
	 * @eparam PLAYER: the player gaining the life
	 * @eparam NUMBER: how much life is being gained
	 * @eparam RESULT: the player that gained life and the amount of life gained
	 */
	public static final EventType GAIN_LIFE_ONE_PLAYER = GainLifeOnePlayer.INSTANCE;

	public static final EventType GAME_OVER = GameOver.INSTANCE;

	/**
	 * Marker event for the decks having been established. This is a hack to
	 * support X-10 modifying the decks after Pack Wars. The correct solution is
	 * to prioritize GameTypeRules' modifyGameState functions. See ticket #381.
	 * 
	 * @eparam RESULT: empty
	 */
	public static final EventType GAME_START = GameStart.INSTANCE;

	/**
	 * @eparam IF: if the set's size is 0, do else, otherwise, do then
	 * @eparam THEN: EventFactory to perform for nonempty
	 * @eparam ELSE: EventFactory to perform for empty
	 * @eparam RESULT: result of THEN or ELSE, depending on how IF evaluated
	 */
	public static final EventType IF_CONDITION_THEN_ELSE = IfConditionThenElse.INSTANCE;

	/**
	 * @eparam IF: EventFactory to perform and test
	 * @eparam THEN: EventFactory to perform on success
	 * @eparam ELSE: EventFactory to perform on failure
	 * @eparam RESULT: results of either THEN or ELSE, depending on whether IF
	 * successfully performed
	 */
	public static final EventType IF_EVENT_THEN_ELSE = IfEventThenElse.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the look
	 * @eparam OBJECT: the objects to be looked at or zones from which to look
	 * at all objects
	 * @eparam PLAYER: who's looking (can be multiple players)
	 * @eparam EFFECT: a set generator saying when the look expires [optional;
	 * if set, this will create a reveal fce with that duration]
	 * @eparam RESULT: the revealed object
	 */
	public static final EventType LOOK = Look.INSTANCE;

	/**
	 * Encapsulates
	 * "look at the top [n] cards of your library, then put them back in any order."
	 * 
	 * @eparam CAUSE: what is causing the look
	 * @eparam PLAYER: who is looking
	 * @eparam TARGET: whose library to look at [optional; default = PLAYER]
	 * @eparam NUMBER: number of cards to look at
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType LOOK_AND_PUT_BACK = LookAndPutBack.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the game loss (must contain one and
	 * exactly one Game.LoseReason)
	 * @eparam PLAYER: the player losing the game (one and exactly one)
	 * @eparam RESULT: the player who lost
	 */
	public static final EventType LOSE_GAME = LoseGame.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the life loss
	 * @eparam PLAYER: the players losing the life
	 * @eparam NUMBER: how much life is being lost
	 * @eparam DAMAGE: required if you are DEAL_DAMAGE_BATCHES (value is
	 * unimportant); prohibited if you aren't.
	 * @eparam RESULT: the players that lost life and each amount of life lost
	 * by a player
	 */
	public static final EventType LOSE_LIFE = LoseLife.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the life loss
	 * @eparam PLAYER: the player losing the life
	 * @eparam NUMBER: how much life is being lost
	 * @eparam DAMAGE: required if the parent LOSE_LIFE event had this
	 * parameter; prohibited otherwise
	 * @eparam RESULT: the player that lost life and how much life was lost
	 */
	public static final EventType LOSE_LIFE_ONE_PLAYER = LoseLifeOnePlayer.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the mill
	 * @eparam NUMBER: how many cards to mill
	 * @eparam PLAYER: who is milling
	 * @eparam RESULT: results of the MOVE_OBJECTS event(s)
	 */
	public static final EventType MILL_CARDS = MillCards.INSTANCE;

	/**
	 * Requires the
	 * {@link org.rnd.jmagic.engine.eventTypes.Monstrosity.MonstrousTracker} !!
	 * Don't forget!
	 * 
	 * @eparam CAUSE: the ability causing this permanent to become monstrous
	 * @eparam OBJECT: the thing becoming monstrous (not redundant, only because
	 * it needs to be returned by {@link EventType#affects()})
	 * @eparam NUMBER: the monstrosity number
	 * @eparam RESULT: empty
	 */
	public static final EventType MONSTROSITY = Monstrosity.INSTANCE;

	/**
	 * This event ACTUALLY MOVES OBJECTS. This event should only be created or
	 * performed in Event.perform().
	 * 
	 * @eparam TARGET: Movements
	 * @eparam RESULT: the new GameObject instances
	 */
	public static final EventType MOVE_BATCH = MoveBatch.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the choice
	 * @eparam NUMBER: how many to choose (integer or range) [optional; default
	 * is 1]
	 * @eparam OBJECT: what is being chosen from
	 * @eparam CHOICE: a ChooseReason for this choice
	 * @eparam TO: where the objects are going
	 * @eparam PLAYER: who is choosing (singular)
	 * @eparam HIDDEN: if present, the new object will be visible to no one.
	 * used for exiling things "face down"
	 * @eparam INDEX: where to insert the object if the zone is ordered (See
	 * {@link ZoneChange#index})
	 * @eparam RESULT: results of the {@link #MOVE_OBJECTS} event
	 */
	public static final EventType MOVE_CHOICE = MoveChoice.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the moving
	 * @eparam FROM: where it's coming from
	 * @eparam TO: where it's going
	 * @eparam NUMBER: how many to move [optional; default is 1]
	 * @eparam COUNTER: the type of counter to move
	 * @eparam RESULT: the counters that were moved
	 */
	public static final EventType MOVE_COUNTERS = MoveCounters.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the moving
	 * @eparam TO: where it's going
	 * @eparam CONTROLLER: who controls the object after it moves [required when
	 * TO is the stack or the battlefield; prohibited when TO is anything else]
	 * @eparam INDEX: where to insert the object if the zone is ordered (See
	 * {@link ZoneChange#index})
	 * @eparam OBJECT: what is being moved
	 * @eparam EFFECT: if present, which side(s) of a split card to cast
	 * @eparam HIDDEN: if present, the new object will be visible to no one.
	 * used for exiling things "face down"
	 * @eparam FACE_DOWN: if present, put the object into the new zone face down
	 * with the characteristics defined by this Class<? extends
	 * {@link Characteristics}> object (see
	 * {@link ZoneChange#faceDownCharacteristics})
	 * @eparam RANDOM: if present, and the objects move to an ordered zone,
	 * those objects will be ordered randomly rather than being ordered by a
	 * player's choice
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam RESULT: {@link ZoneChange}s that will eventually be carried out
	 * by {@link EventType#MOVE_BATCH}
	 */
	public static final EventType MOVE_OBJECTS = MoveObjects.INSTANCE;

	/**
	 * @eparam PLAYER: the player mulliganning
	 * @eparam RESULT: Empty
	 */
	public static final EventType MULLIGAN = Mulligan.INSTANCE;

	/**
	 * @eparam PLAYER: the players mulliganning
	 * @eparam RESULT: The players who chose not to keep
	 */
	public static final EventType MULLIGAN_SIMULTANEOUS = MulliganSimultaneous.INSTANCE;

	/**
	 * @eparam CAUSE: the cause of the event
	 * @eparam EVENT: an EventFactory for the cost to be payed
	 * @eparam NUMBER: the number of times to pay it
	 * @eparam RESULT: the results of all the cost events
	 */
	public static final EventType PAY_CUMULATIVE_UPKEEP = PayCumulativeUpkeep.INSTANCE;

	/**
	 * @eparam CAUSE: what is asking for life payment
	 * @eparam PLAYER: what player is paying the life (singular!)
	 * @eparam NUMBER: how much life is being paid
	 * @eparam RESULT: the player who paid life and the amount of life paid
	 */
	public static final EventType PAY_LIFE = PayLife.INSTANCE;

	/**
	 * @eparam CAUSE: what is asking the player to pay mana
	 * @eparam OBJECT: if this payment is the cost to play an object, that
	 * object. [required for {@link EventType#CAST_SPELL_OR_ACTIVATE_ABILITY}
	 * and {@link EventType#PAY_MANA_COST}; prohibited otherwise]
	 * @eparam COST: what mana cost is being paid (either mana symbols or a
	 * single mana pool) [optional; default is "any amount of mana"]
	 * @eparam PLAYER: who is being asked to pay
	 * @eparam NUMBER: the number of times to pay that cost [optional; default
	 * is 1]
	 * @eparam RESULT: the mana used to pay the cost
	 */
	public static final EventType PAY_MANA = PayMana.INSTANCE;

	/**
	 * NOTE -- THIS EVENT DOES NOT ACCEPT A COST! If you're looking for that,
	 * you want {@link EventType#PAY_MANA}!!
	 * 
	 * @eparam CAUSE: what is asking for the payment
	 * @eparam PLAYER: who is paying
	 * @eparam OBJECT: the object for which to pay the mana cost
	 * @eparam RESULT: empty (should this be something meaningful?)
	 */
	public static final EventType PAY_MANA_COST = PayManaCost.INSTANCE;

	/**
	 * @eparam CAUSE: what is letting the player play the card
	 * @eparam PLAYER: the player playing the card
	 * @eparam OBJECT: the object being played
	 * @eparam ALTERNATE_COST: the alternate cost for playing the object
	 * [optional]
	 * @eparam RESULT: the object after it's played
	 */
	public static final EventType PLAY_CARD = PlayCard.INSTANCE;

	/**
	 * @eparam ACTION: what action is being used to play the land
	 * @eparam PLAYER: the player playing the land
	 * @eparam LAND: the land being played
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PLAY_LAND = PlayLand.INSTANCE;

	/**
	 * Don't feel the need to use this unless it's the "only way". Performing
	 * choices directly via {@link Player}'s choose methods is fine. This event
	 * type is for when you can avoid a custom event type entirely using this
	 * type and {@link EffectResult}.
	 * 
	 * @eparam PLAYER: who is choosing
	 * @eparam NUMBER: how many choices are required [optional; default is 1]
	 * @eparam CHOICE: what to choose from
	 * @eparam TYPE: a {@link PlayerInterface.ChoiceType} and a
	 * {@link PlayerInterface.ChooseReason}
	 * @eparam OBJECT: what object is causing the choice [required when the
	 * {@link PlayerInterface.ChooseReason} in TYPE contains a "~"]
	 * @eparam RESULT: what was chosen
	 */
	public static final EventType PLAYER_CHOOSE = PlayerChoose.INSTANCE;

	/**
	 * Don't feel the need to use this unless it's the "only way". Performing
	 * choices directly via {@link Player}'s choose methods is fine. This event
	 * type is for when you can avoid a custom event type entirely using this
	 * type and {@link EffectResult}.
	 * 
	 * @eparam PLAYER: who is choosing
	 * @eparam CHOICE: {@link org.rnd.util.NumberRange} (use a {@link Between})
	 * @eparam TYPE: a {@link String} describing the choice
	 * @eparam RESULT: what was chosen
	 */
	public static final EventType PLAYER_CHOOSE_NUMBER = PlayerChooseNumber.INSTANCE;

	/**
	 * @eparam PLAYER: the player being given the choice
	 * @eparam EVENT: an EventFactory describing the event to perform
	 * @eparam TARGET: what the choice is for (must be an {@link Identified};
	 * optional)
	 * @eparam RESULT: the players YES/NO response, empty if the event is
	 * impossible
	 */
	public static final EventType PLAYER_MAY = PlayerMay.INSTANCE;

	/**
	 * @eparam PLAYER: the player being given the choice
	 * @eparam OBJECT: the card to cast
	 * @eparam ALTERNATE_COST: a forced alternate cost as per
	 * CAST_SPELL_OR_ACTIVATE_ABILITY
	 * @eparam RESULT: the spell on the stack, if it is cast this way; empty
	 * otherwise
	 */
	public static final EventType PLAYER_MAY_CAST = PlayerMayCast.INSTANCE;

	/**
	 * @eparam CAUSE: what is asking the player to pay mana
	 * @eparam COST: what mana cost is being paid (use a ManaPool)
	 * @eparam NUMBER: how many times to pay the cost (integer only) [optional;
	 * default is 1]
	 * @eparam PLAYER: who is being asked to pay
	 * @eparam RESULT: the result of the PLAYER_MAY (TODO : this is bad since
	 * they might choose to pay and fail; this doesn't affect the outcome of any
	 * cards currently but it might in the future.)
	 */
	public static final EventType PLAYER_MAY_PAY_MANA = PlayerMayPayMana.INSTANCE;

	/**
	 * @eparam CAUSE: what is asking the player to pay mana
	 * @eparam PLAYER: who is being asked to pay
	 * @eparam MANA: mana in addition to X that must be paid (for example, if
	 * the player may pay (X)(R), pass (R) here) [optional; default is nothing]
	 * @eparam RESULT: value of X if they paid, empty if they didn't
	 */
	public static final EventType PLAYER_MAY_PAY_X = PlayerMayPayX.INSTANCE;

	/**
	 * 
	 * 701.27. Populate
	 * 
	 * 701.27a To populate means to choose a creature token you control and put
	 * a token onto the battlefield that's a copy of that creature token.
	 * 
	 * 701.27b If you control no creature tokens when instructed to populate,
	 * you won't put a token onto the battlefield.
	 * 
	 * @eparam CAUSE: what is causing the populate
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: empty
	 */
	public static final EventType POPULATE = Populate.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the proliferation
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: empty
	 */
	public static final EventType PROLIFERATE = Proliferate.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting the counter
	 * @eparam CHOICE: set of objects to choose one from
	 * @eparam COUNTER: the counterType being added
	 * @eparam PLAYER: who is choosing
	 * @eparam RESULT: results of the {@link EventType#PUT_COUNTERS} event(s)
	 */
	public static final EventType PUT_COUNTER_ON_CHOICE = PutCounterOnChoice.INSTANCE;

	/**
	 * Do not use this event in {@link SimpleEventPattern}s. Use the
	 * {@link CountersPlacedPattern} instead.
	 * 
	 * @eparam CAUSE: what is putting the counter
	 * @eparam COUNTER: the counterTypes being added
	 * @eparam NUMBER: the number of the counter to put [optional; default is 1]
	 * @eparam OBJECT: the object to put the counters on
	 * @eparam RESULT: results of the {@link EventType#PUT_ONE_COUNTER} event(s)
	 */
	public static final EventType PUT_COUNTERS = PutCounters.INSTANCE;

	/**
	 * @eparam CAUSE: what is moving the objects
	 * @eparam CONTROLLER: who will control the objects
	 * @eparam ZONE: which zone to put the objects into
	 * @eparam OBJECT: the objects to move
	 * @eparam EFFECT: if present, the side(s) of the split card chosen to cast
	 * @eparam FACE_DOWN: if the object is to be put into the new zone with the
	 * face down status, a Characteristics class defining the characteristics
	 * for that object to assume while face down (see
	 * {@link ZoneChange#faceDownCharacteristics})
	 * @eparam INDEX: where to insert the object if the zone is ordered
	 * [required if zone is ordered; not permitted otherwise] (See
	 * {@link ZoneChange#index})
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam RESULT: result of the {@link #MOVE_OBJECTS} event(s)
	 */
	public static final EventType PUT_IN_CONTROLLED_ZONE = PutInControlledZone.INSTANCE;

	/**
	 * @eparam CAUSE: what is moving the cards
	 * @eparam INDEX: where to put the cards 1 = top, 2 = second from top, 3 =
	 * third from top... -1 = bottom, -2 = second from bottom... [optional;
	 * default = top]
	 * @eparam OBJECT: cards being moved
	 * @eparam RESULT: results of the MOVE_OBJECTS event(s)
	 */
	public static final EventType PUT_INTO_GRAVEYARD = PutIntoGraveyard.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the bounce
	 * @eparam PERMANENT: what is being bounced
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType PUT_INTO_HAND = PutIntoHand.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the bounce
	 * @eparam PLAYER: the players that are bouncing (and thus choosing)
	 * @eparam NUMBER: number of permanents to bounce
	 * @eparam CHOICE: set of permanents to choose from
	 * @eparam RESULT: the results of the BOUNCE events
	 */
	public static final EventType PUT_INTO_HAND_CHOICE = PutIntoHandChoice.INSTANCE;

	/**
	 * @eparam CAUSE: what is moving the cards
	 * @eparam INDEX: where to insert the object (See {@link ZoneChange#index})
	 * @eparam OBJECT: cards being moved; can be cards owned by multiple players
	 * @eparam RANDOM: if present, cards will be put into libraries in a random
	 * order (and thus players won't be asked to order their cards)
	 * @eparam RESULT: results of the MOVE_OBJECT event(s)
	 */
	public static final EventType PUT_INTO_LIBRARY = PutIntoLibrary.INSTANCE;

	/**
	 * @eparam CAUSE: what is moving the object
	 * @eparam CONTROLLER: player who controls it on the battlefield [required
	 * when TO is the battlefield or the stack, prohibited any other time]
	 * @eparam OBJECT: the object to move (singular)
	 * @eparam SOURCE: the object it will be copying
	 * @eparam TYPE: Any {@link Type}s, {@link SuperType}s, or {@link SubType}s
	 * to add as part of the copying process
	 * @eparam TO: the zone to put it into/on top of
	 * @eparam RESULT: the result of the put onto the battlefield event
	 */
	public static final EventType PUT_INTO_ZONE_AS_A_COPY_OF = PutIntoZoneAsACopyOf.INSTANCE;

	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * Do not use this event in {@link SimpleEventPattern}s. Use the
	 * {@link CountersPlacedPattern} instead.
	 * 
	 * @eparam CAUSE: what is putting the counter
	 * @eparam COUNTER: the counterType to add
	 * @eparam OBJECT: the object to put the counter on
	 * @eparam RESULT: the counter as it exists on the object
	 */
	public static final EventType PUT_ONE_COUNTER = PutOneCounter.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam RESULT: result of the {@link #PUT_IN_CONTROLLED_ZONE} event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD = PutOntoBattlefield.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam CHOICE: the things to choose from
	 * @eparam NUMBER: the number of things to choose (an integer)
	 * @eparam SOURCE: the object to store the choice on
	 * @eparam TYPE: the ChoiceType of the choice to be made, and a ChooseReason
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_AND_CHOOSE = PutOntoBattlefieldAndChoose.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing(s) being put onto the battlefield
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam TARGET: the object to attach to
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_ATTACHED_TO = PutOntoBattlefieldAttachedTo.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam PLAYER: the player choosing the objects [optional; default is
	 * CONTROLLER]
	 * @eparam OBJECT: the thing being put onto the battlefield (one object
	 * only)
	 * @eparam CHOICE: the choices of things to attach to
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_ATTACHED_TO_CHOICE = PutOntoBattlefieldAttachedToChoice.INSTANCE;

	/**
	 * @eparam ATTACKER: the attackingID to assign it [optional; default = the
	 * player chooses when this event performs]
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_ATTACKING = PutOntoBattlefieldAttacking.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the choices of things to put onto the battlefield
	 * @eparam NUMBER: the number of choices to make (can be an integer or a
	 * {@link org.rnd.util.NumberRange} [optional; default is 1]
	 * @eparam PLAYER: the player choosing the objects [optional; default is
	 * CONTROLLER]
	 * @eparam EFFECT: the event type to use to put the object onto the
	 * battlefield. [optional; default is {@link #PUT_ONTO_BATTLEFIELD}]
	 * @eparam RESULT: result of the {@link #PUT_ONTO_BATTLEFIELD} event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_CHOICE = PutOntoBattlefieldChoice.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the choices of things to put onto the battlefield
	 * @eparam NUMBER: the number of choices to make [optional; default is 1]
	 * @eparam PLAYER: the player choosing the objects [optional; default is
	 * CONTROLLER]
	 * @eparam TARGET: the object to attach to
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD_ATTACHED_TO event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_CHOICE_ATTACHED_TO = PutOntoBattlefieldChoiceAttachedTo.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TAPPED = PutOntoBattlefieldTapped.INSTANCE;

	/**
	 * @eparam ATTACKER: the attackingID to assign it [optional; default = the
	 * player chooses when this event performs]
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING = PutOntoBattlefieldTappedAndAttacking.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam NUMBER: how many counters (default is 1)
	 * @eparam COUNTER: the counterTypes to add
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TAPPED_WITH_COUNTERS = PutOntoBattlefieldTappedWithCounters.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TRANSFORMED = PutOntoBattlefieldTransformed.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting things into play
	 * @eparam OBJECT: what is being put into play
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL = PutOntoBattlefieldUnderOwnerControl.INSTANCE;

	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam NUMBER: how many counters (default is 1)
	 * @eparam COUNTER: the counterTypes to add
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_WITH_COUNTERS = PutOntoBattlefieldWithCounters.INSTANCE;

	/**
	 * @eparam CAUSE: the reason its being regenerated
	 * @eparam OBJECT: the object being regenerated
	 * @eparam RESULT: the objects that were regenerated
	 */
	public static final EventType REGENERATE = Regenerate.INSTANCE;

	/**
	 * @eparam CAUSE: what is removing the counter
	 * @eparam COUNTER: the counter types to remove [optional; default = all
	 * counter types]
	 * @eparam OBJECT: the object to remove the counters from
	 * @eparam RESULT: results of the REMOVE_ONE_COUNTER event(s)
	 */
	public static final EventType REMOVE_ALL_COUNTERS = RemoveAllCounters.INSTANCE;

	/**
	 * @eparam CAUSE: what is removing the counter
	 * @eparam COUNTER: the counterTypes being removed
	 * @eparam NUMBER: the number of the counters to remove [optional; default
	 * is all]
	 * @eparam OBJECT: the object or player to remove the counters from
	 * @eparam RESULT: results of the {@link #REMOVE_ONE_COUNTER} event(s)
	 */
	public static final EventType REMOVE_COUNTERS = RemoveCounters.INSTANCE;

	/**
	 * @eparam CAUSE: the cause
	 * @eparam COUNTER: the counters to choose from [optional when only one
	 * object is specified in OBJECT; defaults to all counters on OBJECT]
	 * @eparam NUMBER: the number of counters to choose (integer or number
	 * range) [optional; defaults to 1]
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: the removed counters
	 */
	public static final EventType REMOVE_COUNTERS_CHOICE = RemoveCountersChoice.INSTANCE;

	/**
	 * @eparam CAUSE: the cause
	 * @eparam COUNTER: the counter type to remove
	 * @eparam OBJECT: the objects to select from (no need to make sure they
	 * have the counters of the right type on them, this event type will do
	 * that)
	 * @eparam NUMBER: the number of counters to remove (integer) [optional;
	 * defaults to 1]
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: the removed counters
	 */
	public static final EventType REMOVE_COUNTERS_FROM_CHOICE = RemoveCountersFromChoice.INSTANCE;

	/**
	 * @eparam OBJECT: GameObjects and Players to be removed from combat
	 * @eparam RESULT: The GameObjects/Players removed from combat
	 */
	public static final EventType REMOVE_FROM_COMBAT = RemoveFromCombat.INSTANCE;

	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is removing the counter
	 * @eparam COUNTER: the counterType being removed
	 * @eparam OBJECT: the object or player to remove the counter from
	 * @eparam RESULT: the removed counter
	 */
	public static final EventType REMOVE_ONE_COUNTER = RemoveOneCounter.INSTANCE;

	/**
	 * @eparam PLAYER: the player to remove counters from
	 * @eparam NUMBER: the number of counters to remove
	 * @eparam RESULT: the counters removed from the players
	 */
	public static final EventType REMOVE_POISON_COUNTERS = RemovePoisonCounters.INSTANCE;

	/**
	 * @eparam CAUSE: what removed the last counter
	 * @eparam COUNTER: the type of counter that was removed, and of which there
	 * are no more on the object
	 * @eparam OBJECT: the object the counter was removed from
	 * @eparam RESULT: empty
	 */
	public static final EventType REMOVED_LAST_COUNTER = RemovedLastCounter.INSTANCE;

	/**
	 * @eparam OBJECT: any objects exempt from the procedure that restarts the
	 * game
	 */
	public static final EventType RESTART_THE_GAME = RestartTheGame.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the reveal
	 * @eparam OBJECT: the objects to reveal, or zones to reveal all objects
	 * from
	 * @eparam EFFECT: a set generator saying when the reveal expires [optional;
	 * if unset, the fce will expire when the cause no longer exists (good
	 * enough for spells and abilities on the stack)]
	 * @eparam RESULT: the revealed object
	 */
	public static final EventType REVEAL = Reveal.INSTANCE;

	/**
	 * @eparam CAUSE: the cause of the reveal
	 * @eparam OBJECT: the objects to choose from
	 * @eparam EFFECT: a set generator saying when the reveal expires [optional;
	 * if set, this will create a reveal fce with that duration]
	 * @eparam NUMBER: the number of cards to choose (single number or a
	 * {@link org.rnd.util.NumberRange}) [optional; default is one]
	 * @eparam PLAYER: the player who chooses what to reveal
	 * @eparam RESULT: the revealed object
	 */
	public static final EventType REVEAL_CHOICE = RevealChoice.INSTANCE;

	/**
	 * @eparam CAUSE: the cause of the reveal
	 * @eparam PLAYER: the players to randomly reveal cards
	 * @eparam NUMBER: the number of cards to randomly reveal
	 * @eparam RESULT: the combined results of REVEAL events
	 */
	public static final EventType REVEAL_RANDOM_FROM_HAND = RevealRandomFromHand.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the sacrifice
	 * @eparam NUMBER: number of permanents to be sacrificed (integer or range)
	 * @eparam CHOICE: set of permanents to choose from; it is sufficient to use
	 * this as a filter -- e.g., sacrifice a land can be represented as simply
	 * HasType(LAND). This does NOT use the double-generator idiom.
	 * @eparam PLAYER: who is choosing and sacrificing
	 * @eparam RESULT: results of the {@link #SACRIFICE_PERMANENTS} event(s)
	 */
	public static final EventType SACRIFICE_CHOICE = SacrificeChoice.INSTANCE;

	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is causing the sacrifice
	 * @eparam PLAYER: who is sacrificing
	 * @eparam PERMANENT: what is being sacrificed
	 * @eparam RESULT: the zone change
	 */
	public static final EventType SACRIFICE_ONE_PERMANENT = SacrificeOnePermanent.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the sacrifice
	 * @eparam PLAYER: who is sacrificing (singular) (not redundant, as an
	 * attempt to sacrifice a permanent you don't control should fail)
	 * @eparam PERMANENT: the permanents being sacrificed
	 * @eparam RESULT: results of the {@link #SACRIFICE_ONE_PERMANENT} event(s)
	 */
	public static final EventType SACRIFICE_PERMANENTS = SacrificePermanents.INSTANCE;

	/**
	 * 701.17. Scry
	 * 
	 * 701.17a To "scry N" means to look at the top N cards of your library, put
	 * any number of them on the bottom of your library in any order, and put
	 * the rest on top of your library in any order.
	 * 
	 * @eparam CAUSE: what is letting the player scry
	 * @eparam PLAYER: the player scrying
	 * @eparam NUMBER: the number of objects to look at
	 * @eparam RESULT: empty
	 */
	public static final EventType SCRY = Scry.INSTANCE;

	/**
	 * 701.14. Search<br>
	 * <br>
	 * 701.14a To search for a card in a zone, look at all cards in that zone
	 * (even if it's a hidden zone) and find a card that matches the given
	 * description.<br>
	 * <br>
	 * 701.14b If a player is searching a hidden zone for cards with a stated
	 * quality, such as a card with a certain card type or color, that player
	 * isn't required to find some or all of those cards even if they're present
	 * in that zone.<br>
	 * <br>
	 * 701.14c If a player is searching a hidden zone simply for a quantity of
	 * cards, such as "a card" or "three cards," that player must find that many
	 * cards (or as many as possible, if the zone doesn't contain enough cards).<br>
	 * <br>
	 * 701.14d If the effect that contains the search instruction doesn't also
	 * contain instructions to reveal the found card(s), then they're not
	 * revealed.<br>
	 * <br>
	 * Despite 701.14d, this event type will reveal the chosen cards if the TYPE
	 * parameter is present. This is for two reasons: 1. To prevent card writers
	 * from needing to explicitly reveal those cards (since every effect that
	 * specifies a restriction also says to reveal the searched-for cards), and
	 * 2. because we're lazy and there used to be a rule saying to reveal the
	 * chosen cards if there was a restriction on the search.<br>
	 * <br>
	 * If you need to do a {@link #SHUFFLE_LIBRARY} after searching, you need to
	 * do it manually.<br>
	 * <br>
	 * To trigger from a search, reference this event type. To prohibit or
	 * replace a search, do not reference this event type; instead reference
	 * {@link EventType#SEARCH_MARKER}.
	 * 
	 * @eparam CAUSE: the reason for the search
	 * @eparam PLAYER: the player searching
	 * @eparam NUMBER: the number of objects to find (can be a range)
	 * @eparam CARD: the cards or zones to search through (any number of zones
	 * (and cards from different zones) is supported)
	 * @eparam TYPE: the restriction on cards to find [optional; default is all]
	 * (requires double generator idiom)
	 * @eparam RESULT: the objects found and the {@link Zone}s searched
	 */
	public static final EventType SEARCH = Search.INSTANCE;

	/**
	 * Search [zones] for all cards matching [condition] and put them in [zone].
	 * E.g. Cranial Extraction.
	 * 
	 * @eparam CAUSE: the reason for the search
	 * @eparam PLAYER: the player searching
	 * @eparam ZONE: the zones to search through
	 * @eparam TYPE: the restriction on cards to find (requires double generator
	 * idiom)
	 * @eparam TO: where to put the cards after they're found
	 * @eparam RESULT: the results of the PLAYER_CHOOSE event
	 */
	public static final EventType SEARCH_FOR_ALL_AND_PUT_INTO = SearchForAllAndPutInto.INSTANCE;

	/**
	 * @eparam CAUSE: the reason for the search
	 * @eparam CONTROLLER: who controls the object after it moves [required when
	 * TO is the stack or the battlefield; prohibited when TO is anything else]
	 * @eparam INDEX: where to insert the object if the zone is ordered
	 * [required when the zone is an ordered zone; not permitted otherwise] (See
	 * {@link ZoneChange#index})
	 * @eparam PLAYER: the player searching
	 * @eparam TARGET: the player whose library will be searched [optional;
	 * default is PLAYER]
	 * @eparam NUMBER: the number of objects to find (can be a NumberRange)
	 * @eparam TO: the zone to put the cards found
	 * @eparam TAPPED: if present, and TO is the battlefield, the object is put
	 * onto the battlefield tapped
	 * @eparam TYPE: the restriction on cards to find [optional; default is all]
	 * (requires double generator idiom)
	 * @eparam HIDDEN: if present, is passed on to MOVE_OBJECTS [optional]
	 * @eparam RESULT: the new object
	 */
	public static final EventType SEARCH_LIBRARY_AND_PUT_INTO = SearchLibraryAndPutInto.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the search
	 * @eparam PLAYER: who is searching
	 * @eparam TYPE: setgenerator restricting what cards can be chosen
	 * [optional; default is all] (requires double generator idiom)
	 * @eparam RESULT: empty
	 */
	public static final EventType SEARCH_LIBRARY_AND_PUT_ON_TOP = SearchLibraryAndPutOnTop.INSTANCE;

	/**
	 * @eparam PLAYER: the player searching
	 * @eparam CARD: the cards/zones being searched
	 * @eparam RESULT: empty
	 */
	public static final EventType SEARCH_MARKER = SearchMarker.INSTANCE;

	/**
	 * @eparam OBJECT: the objects to set the attacking id on
	 * @eparam ATTACKER: an integer to set the id to
	 * @eparam RESULT: empty
	 */
	public static final EventType SET_ATTACKING_ID = SetAttackingID.INSTANCE;

	/**
	 * @eparam CAUSE: what is setting the life
	 * @eparam NUMBER: the new life total
	 * @eparam PLAYER: whose life is being set
	 * @eparam RESULT: each player whose life was changed
	 */
	public static final EventType SET_LIFE = SetLife.INSTANCE;

	/**
	 * TODO: this event performs a top level move because performing a shuffle
	 * after it is harder otherwise
	 * 
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam OBJECT: the cards to shuffle in and the players whose libraries
	 * are being shuffled (not redundant, since
	 * "shuffle your graveyard into your library" should shuffle your library
	 * even with no cards in your graveyard)
	 * @eparam RESULT: the cards as they exist in the library, and the shuffled
	 * library
	 */
	public static final EventType SHUFFLE_INTO_LIBRARY = ShuffleIntoLibrary.INSTANCE;

	/**
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam PLAYER: the player choosing cards (singular)
	 * @eparam NUMBER: the number of cards to shuffle in (integer or range)
	 * @eparam CHOICE: the choice of cards to shuffle in
	 * @eparam RESULT: the result of SHUFFLE_INTO_LIBRARY
	 */
	public static final EventType SHUFFLE_INTO_LIBRARY_CHOICE = ShuffleIntoLibraryChoice.INSTANCE;

	/**
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam PLAYER: players shuffling their libraries
	 * @eparam RESULT: the shuffled libraries
	 */
	public static final EventType SHUFFLE_LIBRARY = ShuffleLibrary.INSTANCE;

	/**
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam PLAYER: player (only one) shuffling their library
	 * @eparam RESULT: the shuffled library
	 */
	public static final EventType SHUFFLE_ONE_LIBRARY = ShuffleOneLibrary.INSTANCE;

	/**
	 * <p>
	 * Permanently reveals an object that is hidden. Cards using this will have
	 * language on them that instructs a player to turn the card face up. We've
	 * chosen not to use the same language to keep it from being confused with
	 * the face-down status of permanents caused by the morph keyword.
	 * </p>
	 * <p>
	 * Contrast with {@link EventType#REVEAL}, which reveals a card for a
	 * limited duration.
	 * </p>
	 * 
	 * @eparam OBJECT: the object(s) to "turn face up".
	 * @eparam RESULT: empty
	 */
	public static final EventType SHOW = Show.INSTANCE;

	/**
	 * @eparam CAUSE: The reason a phase is being added
	 * @eparam PHASE: A {@link java.util.List} of
	 * {@link org.rnd.jmagic.engine.Phase.PhaseType} specifying which kind of
	 * phase to create; the phases created will be taken in the order given
	 * @eparam TARGET: Which {@link Phase} to add the phase after. If this
	 * parameter is empty or the target phase has already occurred, this event
	 * won't create extra phases, and will fail (return false).
	 * @eparam RESULT: The created phase(s)
	 */
	public static final EventType TAKE_EXTRA_PHASE = TakeExtraPhase.INSTANCE;

	/**
	 * @eparam CAUSE: the reason turns are being added
	 * @eparam PLAYER: the player to take the turn
	 * @eparam NUMBER: the number of extra turns to add [optional; default is 1]
	 * @eparam STEP: the StepType of any steps that should be skipped
	 * @eparam RESULT: the turns that were created
	 */
	public static final EventType TAKE_EXTRA_TURN = TakeExtraTurn.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam PLAYER: who is choosing
	 * @eparam CHOICE: what to choose from
	 * @eparam NUMBER: how many to tap (integer or range)
	 * @eparam RESULT: the tapped object
	 */
	public static final EventType TAP_CHOICE = TapChoice.INSTANCE;

	/**
	 * Represents "Tap [x]. Those objects don't untap during their controllers'
	 * next untap steps."
	 * 
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam OBJECT: what is being tapped
	 * @eparam RESULT: the tapped objects
	 */
	public static final EventType TAP_HARD = TapHard.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam OBJECT: what is being tapped
	 * @eparam RESULT: the tapped object
	 */
	public static final EventType TAP_ONE_PERMANENT = TapOnePermanent.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam OBJECT: what is being tapped
	 * @eparam RESULT: the tapped objects
	 */
	public static final EventType TAP_PERMANENTS = TapPermanents.INSTANCE;

	/**
	 * @eparam CAUSE: what is causing the text change
	 * @eparam TARGET: what to change the text of
	 * @eparam EFFECT: SetGenerator for the expires field of the effect created
	 * (optional; default is "until cleanup")
	 * @eparam RESULT: Empty.
	 */
	public static final EventType TEXT_CHANGE_COLOR_OR_BASIC_LAND_TYPE = TextChangeColorOrBasicLandType.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the turning face down
	 * @eparam OBJECT: what permanents to turn face down
	 * @eparam RESULT: the permanents that were turned face down
	 */
	public static final EventType TURN_PERMANENTS_FACE_DOWN = TurnPermanentsFaceDown.INSTANCE;

	/**
	 * @eparam OBJECT: the card to transform
	 * @eparam RESULT: empty
	 */
	public static final EventType TRANSFORM_ONE_PERMANENT = TransformOnePermanent.INSTANCE;

	/**
	 * @eparam OBJECT: the cards to transform
	 * @eparam RESULT: empty
	 */
	public static final EventType TRANSFORM_PERMANENT = TransformPermanent.INSTANCE;

	/**
	 * Marker event for when an opponent chooses to have a creature with Tribute
	 * enter the battlefield with counters.
	 * 
	 * @eparam OBJECT: the object whose tribute is being paid
	 * @eparam RESULT: the object whose tribute is being paid (for ease of use
	 * in the tribute tracker)
	 */
	public static final EventType TRIBUTE_PAID = TributePaid.INSTANCE;

	/**
	 * Note that this only operates on a single GameObject. This is allowed to
	 * be invoked directly, unlike the other *_ONE_* EventType.
	 * 
	 * @eparam CAUSE: what is doing the turning face up
	 * @eparam OBJECT: what permanent to turn face up
	 * @eparam RESULT: the permanents that were turned face up
	 */
	public static final EventType TURN_PERMANENT_FACE_UP = TurnPermanentFaceUp.INSTANCE;

	/**
	 * @eparam OBJECT: the GameObject instances already attached
	 * @eparam RESULT: the AttachedBy instances which were previously attached
	 * to
	 */
	public static final EventType UNATTACH = Unattach.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the untapping
	 * @eparam PLAYER: who is choosing
	 * @eparam CHOICE: what to choose from
	 * @eparam NUMBER: how many to untap (integer or range)
	 * @eparam RESULT: the tapped object
	 */
	public static final EventType UNTAP_CHOICE = UntapChoice.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the untapping
	 * @eparam OBJECT: what is being untapped
	 * @eparam RESULT: the untapped object
	 */
	public static final EventType UNTAP_ONE_PERMANENT = UntapOnePermanent.INSTANCE;

	/**
	 * @eparam CAUSE: what is doing the untapping
	 * @eparam OBJECT: what is being untapped
	 * @eparam RESULT: the untapped objects
	 */
	public static final EventType UNTAP_PERMANENTS = UntapPermanents.INSTANCE;

	/**
	 * @eparam CAUSE: who told the player to win
	 * @eparam PLAYER: the player that should win (TODO: allow multiple players
	 * to win)
	 * @eparam RESULT: nothing. an exception gets thrown, so we don't even set
	 * it up.
	 */
	public static final EventType WIN_GAME = WinGame.INSTANCE;

	/**
	 * @eparam CAUSE: the wish
	 * @eparam CHOICE: the available choices
	 * @eparam PLAYER: the chooser
	 * @eparam RESULT: empty
	 * 
	 * TODO : This probably isn't possible, if multiple players win the game
	 * simultaneously via a replacement effect (LOL), and it ends up being
	 * executed as multiple simultaneous WIN_GAMEs, only the "first" player will
	 * actually win here. We need to have the flag set by this event but only
	 * throw the exception after all possible losses and wins have been set up
	 * (possibly in Event.perform, if the event is top level).
	 * 
	 * In general, we need to make sure that an event type's perform method is
	 * simultaneous-friendly.
	 */
	public static final EventType WISH = Wish.INSTANCE;

	/**
	 * Creates an event with the intent of immediately performing it. This event
	 * is protected to prevent its use outside the context of an EventType.
	 * 
	 * @param game The game whose physical state will be passed to the event
	 * constructor.
	 * @param name The name to pass to the event constructor.
	 * @param type The type to pass to the event constructor.
	 * @param parameters The parameter map to pass to the event constructor.
	 * @return The new event.
	 */
	protected static final Event createEvent(Game game, String name, EventType type, java.util.Map<Parameter, Set> parameters)
	{
		Event newEvent = new Event(game.physicalState, name, type);

		if(parameters != null)
			for(java.util.Map.Entry<Parameter, Set> parameter: parameters.entrySet())
				newEvent.parameters.put(parameter.getKey(), Identity.fromCollection(parameter.getValue()));

		return newEvent;
	}

	/**
	 * Gets a number range from a parameter when that parameter is allowed to
	 * contain a number or a NumberRange.
	 * 
	 * @param parameter A set from a parameter map to an EventType.
	 * @return If <code>parameter</code> contains a number range, that
	 * NumberRange; otherwise, a NumberRange whose lower and upper bounds are
	 * each the sum of the integers in <code>parameter</code>. (1, 1) if
	 * <code>parameter</code> is null.
	 */
	public static org.rnd.util.NumberRange getRange(Set parameter)
	{
		if(parameter == null)
			return new org.rnd.util.NumberRange(1, 1);

		org.rnd.util.NumberRange num = parameter.getOne(org.rnd.util.NumberRange.class);
		if(num == null)
		{
			int sum = Sum.get(parameter);
			if(sum < 0)
				sum = 0;
			num = new org.rnd.util.NumberRange(sum, sum);
		}
		return num;
	}

	private static java.util.Map<String, Class<? extends EventType>> typeNames;

	private final String toString;

	/**
	 * @param name Be nice and make this the name of the constant that stores
	 * the singleton instance of this event type.
	 */
	public EventType(String name)
	{
		if(null == typeNames)
			typeNames = new java.util.HashMap<String, Class<? extends EventType>>();
		if(typeNames.containsKey(name) && !typeNames.get(name).equals(this.getClass()))
			throw new UnsupportedOperationException("EventType names must be unique");
		typeNames.put(name, this.getClass());

		this.toString = name;
	}

	/**
	 * Override this function for any event type that adds mana to a mana pool.
	 * If it does add mana, that mana must be specified in the MANA parameter.
	 * Also, the symbols created and adding to a mana pool must be part of the
	 * event's RESULT.
	 * 
	 * @return Whether this event type adds mana to a mana pool.
	 */
	public boolean addsMana()
	{
		return false;
	}

	public abstract Parameter affects();

	/**
	 * attempt() returns true if, given all the assumptions you make when
	 * overriding perform() are true, the event is able to be performed.
	 * Example: TAP's attempt method returns false if any of the objects to be
	 * tapped are already tapped. If, given all the assumptions you make when
	 * overriding perform() are true, the event is always able to be performed,
	 * don't override attempt().
	 * 
	 * @param game The game in which the event is being attempted.
	 * @param event The event being attempted.
	 * @param parameters The parameters of the event to attempt.
	 * @return Whether the event is able to be performed.
	 */
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		return true;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		EventType other = (EventType)obj;
		if(this.toString == null)
		{
			if(other.toString != null)
				return false;
		}
		else if(!this.toString.equals(other.toString))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.toString == null) ? 0 : this.toString.hashCode());
		return result;
	}

	/**
	 * Causes choices for an event to be made, such as which creature to
	 * sacrifice to Innocent Blood or put onto the battlefield from Hunted
	 * Wumpus.
	 * 
	 * Event types where choices are required should override this method to
	 * make those choices and store them in <code>event</code>. Theoretically,
	 * you only need to override this method if your event type can be used as a
	 * cost or if multiple players are making choices for a simultaneous action;
	 * however it's good practice to override it anyway. Ignore the man behind
	 * the PUT_ONTO_BATTLEFIELD*_CHOICE* curtain. He's a bad programmer and he's
	 * lazy.
	 * 
	 * Properly overriding this method involves calling
	 * <code>event.putChoices</code> for each player that makes a choice, and
	 * setting <code>event.allChoicesMade</code> to false if a player is asked
	 * to make more choices than there are available (or for some other reason
	 * is unable to make a required choice).
	 * 
	 * @param game What game the choice is made in.
	 * @param event What even the choice is made for.
	 * @param parameters Parameters for the event.
	 */
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// By default no choices need to be made.
	}

	/**
	 * When overriding this method, you may assume:
	 * <ul>
	 * <li>all the objects in the affected parameter exist</li>
	 * <li>the event wasn't replaced</li>
	 * <li>the event wasn't prohibited</li>
	 * </ul>
	 * 
	 * @param game The game the event is being performed in
	 * @param event The event being performed
	 * @param parameters The parameters to the event
	 * @return Whether the event can be used to pay a cost
	 */
	public abstract boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters);

	@Override
	public String toString()
	{
		return this.toString;
	}
}
