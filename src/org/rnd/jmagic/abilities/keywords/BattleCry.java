package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Battle cry")
public final class BattleCry extends Keyword
{
	public BattleCry(GameState state)
	{
		super(state, "Battle cry");
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new BattleCryAbility(this.state));
		return ret;
	}

	public static final class BattleCryAbility extends EventTriggeredAbility
	{
		public BattleCryAbility(GameState state)
		{
			super(state, "When this attacks, each other attacking creature gets +1/+0 until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			SetGenerator eachOtherAttackingCreature = RelativeComplement.instance(Intersect.instance(Attacking.instance(), CreaturePermanents.instance()), ABILITY_SOURCE_OF_THIS);
			this.addEffect(createFloatingEffect("Each other attacking creature gets +1/+0 until end of turn.", modifyPowerAndToughness(eachOtherAttackingCreature, +1, +0)));
		}
	}
}
