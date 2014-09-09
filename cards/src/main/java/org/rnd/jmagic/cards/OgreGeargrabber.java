package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ogre Geargrabber")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.WARRIOR})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class OgreGeargrabber extends Card
{
	public static final class OgreGeargrabberAbility0 extends EventTriggeredAbility
	{
		public OgreGeargrabberAbility0(GameState state)
		{
			super(state, "Whenever Ogre Geargrabber attacks, gain control of target Equipment an opponent controls until end of turn. Attach it to Ogre Geargrabber. When you lose control of that Equipment, unattach it.");
			this.addPattern(whenThisAttacks());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target Equipment an opponent controls"));

			ContinuousEffect.Part control = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			control.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			control.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of target Equipment an opponent controls until end of turn.", control));

			EventFactory attach = new EventFactory(EventType.ATTACH, "Attach it to Ogre Geargrabber.");
			attach.parameters.put(EventType.Parameter.OBJECT, target);
			attach.parameters.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			this.addEffect(attach);

			SimpleEventPattern loseControl = new SimpleEventPattern(EventType.CHANGE_CONTROL);
			loseControl.put(EventType.Parameter.OBJECT, target);
			loseControl.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory unattach = new EventFactory(EventType.UNATTACH, "Unattach it.");
			unattach.parameters.put(EventType.Parameter.OBJECT, target);

			EventFactory trigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When you lose control of that Equipment, unattach it.");
			trigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			trigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(loseControl));
			trigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(unattach));
			this.addEffect(trigger);
		}
	}

	public OgreGeargrabber(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever Ogre Geargrabber attacks, gain control of target Equipment
		// an opponent controls until end of turn. Attach it to Ogre
		// Geargrabber. When you lose control of that Equipment, unattach it.
		this.addAbility(new OgreGeargrabberAbility0(state));
	}
}
