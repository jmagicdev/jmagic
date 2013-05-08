package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public class Soulbond extends Keyword
{
	public static final class AnotherCreatureEntersTheBattlefield extends EventTriggeredAbility
	{
		public AnotherCreatureEntersTheBattlefield(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, if you control both that creature and this one and both are unpaired, you may pair that creature with this creature for as long as both remain creatures on the battlefield under your control.");
			SetGenerator thisCreature = ABILITY_SOURCE_OF_THIS;
			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), thisCreature);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), otherCreatures, You.instance(), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator youControlUnpairedThis = Intersect.instance(youControl, Unpaired.instance(), thisCreature);
			SetGenerator youControlUnpairedThat = Intersect.instance(youControl, Unpaired.instance(), thatCreature);
			this.interveningIf = Both.instance(youControlUnpairedThis, youControlUnpairedThat);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PAIR);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Union.instance(thisCreature, thatCreature));

			SetGenerator youControlCreatureThis = Intersect.instance(CREATURES_YOU_CONTROL, thisCreature);
			SetGenerator youControlCreatureThat = Intersect.instance(CREATURES_YOU_CONTROL, thatCreature);
			SetGenerator expires = Not.instance(Both.instance(youControlCreatureThis, youControlCreatureThat));
			EventFactory floatingEffect = createFloatingEffect(expires, "Pair this creature with that creature", part);

			this.addEffect(youMay(floatingEffect));
		}
	}

	public static final class ThisEntersTheBattlefield extends EventTriggeredAbility
	{
		public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Soulbond", "Choose another unpaired creature you control to pair with.", true);

		public ThisEntersTheBattlefield(GameState state)
		{
			super(state, "When this creature enters the battlefield, if you control both this creature and another creature and both are unpaired, you may pair this creature with another unpaired creature you control for as long as both remain creatures on the battlefield under your control.");
			SetGenerator thisCreature = ABILITY_SOURCE_OF_THIS;
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), thisCreature, false));

			SetGenerator otherCreaturesYouControl = RelativeComplement.instance(CREATURES_YOU_CONTROL, thisCreature);
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator youControlUnpairedThis = Intersect.instance(youControl, Unpaired.instance(), thisCreature);
			SetGenerator youControlUnpairedOtherCreatures = Intersect.instance(Unpaired.instance(), otherCreaturesYouControl);
			this.interveningIf = Both.instance(youControlUnpairedThis, youControlUnpairedOtherCreatures);

			EventFactory choose = playerChoose(You.instance(), 1, youControlUnpairedOtherCreatures, PlayerInterface.ChoiceType.OBJECTS, REASON, "Choose another unpaired creature you control");
			SetGenerator thatCreature = EffectResult.instance(choose);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PAIR);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Union.instance(thisCreature, thatCreature));

			SetGenerator youControlCreatureThis = Intersect.instance(CREATURES_YOU_CONTROL, thisCreature);
			SetGenerator youControlCreatureThat = Intersect.instance(CREATURES_YOU_CONTROL, thatCreature);
			SetGenerator expires = Not.instance(Both.instance(youControlCreatureThis, youControlCreatureThat));
			EventFactory floatingEffect = createFloatingEffect(expires, "and pair this creature with the chosen creature.", part);

			this.addEffect(youMay(sequence(choose, floatingEffect), "You may pair this creature with another unpaired creature you control for as long as both remain creatures on the battlefield under your control."));
		}
	}

	public Soulbond(GameState state)
	{
		super(state, "Soulbond");
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new ThisEntersTheBattlefield(this.state));
		ret.add(new AnotherCreatureEntersTheBattlefield(this.state));
		return ret;
	}
}
