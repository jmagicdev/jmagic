package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Flanking")
public final class Flanking extends Keyword
{
	public Flanking(GameState state)
	{
		super(state, "Flanking");
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new FlankingTrigger(this.state));
		return ret;
	}

	public static final class FlankingTrigger extends EventTriggeredAbility
	{
		public FlankingTrigger(GameState state)
		{
			super(state, "Whenever this creature becomes blocked by a creature without flanking, the blocking creature gets -1/-1 until end of turn.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator hasFlanking = HasKeywordAbility.instance(Flanking.class);
			SetGenerator creaturesWithoutFlanking = RelativeComplement.instance(CreaturePermanents.instance(), hasFlanking);
			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator blocker = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.ATTACKER, thisCard);
			pattern.put(EventType.Parameter.OBJECT, creaturesWithoutFlanking);
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(blocker, -1, -1, "The blocking creature gets -1/-1 until end of turn."));
		}
	}
}
