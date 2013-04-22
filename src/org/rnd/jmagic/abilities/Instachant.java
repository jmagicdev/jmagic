package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Instachant extends StaticAbility
{
	private String cardName;

	public Instachant(GameState state, String cardName)
	{
		super(state, "You may cast " + cardName + " as though it had flash. If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.");
		this.cardName = cardName;

		SetGenerator delayedTriggeredAbility = This.instance();
		SetGenerator necromancyInHand = AbilitySource.instance(delayedTriggeredAbility);
		SetGenerator necromancyOnStack = FutureSelf.instance(necromancyInHand);
		SetGenerator thePermanentItBecomes = FutureSelf.instance(necromancyOnStack);
		EventFactory sacrifice = sacrificeSpecificPermanents(ControllerOf.instance(thePermanentItBecomes), thePermanentItBecomes, "The controller of the permanent it becomes sacrifices it.");

		SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
		pattern.put(EventType.Parameter.STEP, CleanupStepOf.instance(Players.instance()));

		EventFactory sacrificeAtCleanup = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.");
		sacrificeAtCleanup.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrificeAtCleanup.parameters.put(EventType.Parameter.EVENT, Identity.instance(pattern));
		sacrificeAtCleanup.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));

		SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());
		PlayPermission permission = new PlayPermission(youHavePriority);
		permission.attachEvent(sacrificeAtCleanup);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_TIMING);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
		part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
		this.addEffectPart(part);

		SetGenerator inAHand = Intersect.instance(This.instance(), InZone.instance(HandOf.instance(Players.instance())));
		SetGenerator cantPlaySorcerySpeed = Not.instance(Intersect.instance(You.instance(), PlayerCanPlaySorcerySpeed.instance()));
		this.canApply = Both.instance(inAHand, cantPlaySorcerySpeed);
	}

	@Override
	public Instachant create(Game game)
	{
		return new Instachant(game.physicalState, this.cardName);
	}
}