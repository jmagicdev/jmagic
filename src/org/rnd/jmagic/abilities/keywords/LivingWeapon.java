package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Living weapon")
public final class LivingWeapon extends Keyword
{
	public LivingWeapon(GameState state)
	{
		super(state, "Living weapon");
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new LivingWeaponAbility(this.state));
		return ret;
	}

	public static final class LivingWeaponAbility extends EventTriggeredAbility
	{
		public LivingWeaponAbility(GameState state)
		{
			super(state, "When this Equipment enters the battlefield, put a 0/0 black Germ creature token onto the battlefield, then attach this to it.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 0, 0, "Put a 0/0 black Germ creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.GERM);
			EventFactory tokenFactory = token.getEventFactory();
			this.addEffect(tokenFactory);

			EventFactory attach = new EventFactory(EventType.ATTACH, "Attach this to it.");
			attach.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			attach.parameters.put(EventType.Parameter.TARGET, EffectResult.instance(tokenFactory));
			this.addEffect(attach);
		}
	}
}
