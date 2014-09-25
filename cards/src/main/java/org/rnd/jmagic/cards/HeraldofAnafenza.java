package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Herald of Anafenza")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class HeraldofAnafenza extends Card
{
	public static final class HeraldOutlast extends SetGenerator
	{
		private HeraldOutlast()
		{
			// singleton
		}

		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new HeraldOutlast();
			return _instance;
		}

		public Set evaluate(GameState state, Identified thisObject)
		{
			return state.stack().objects.stream()//
			.filter(o -> //

			o instanceof org.rnd.jmagic.abilities.keywords.Outlast.OutlastAbility && //
			((ActivatedAbility)o).getSourceID() == thisObject.ID) //

			.collect(java.util.stream.Collectors.toCollection(Set::new));
		}
	}

	public static final class HeraldofAnafenzaAbility1 extends EventTriggeredAbility
	{
		public HeraldofAnafenzaAbility1(GameState state)
		{
			super(state, "Whenever you activate Herald of Anafenza's outlast ability, put a 1/1 white Warrior creature token onto the battlefield.");

			SimpleEventPattern activate = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			activate.put(EventType.Parameter.OBJECT, HeraldOutlast.instance());
			this.addPattern(activate);

			CreateTokensFactory warrior = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Warrior creature token onto the battlefield.");
			warrior.setColors(Color.WHITE);
			warrior.setSubTypes(SubType.WARRIOR);
			this.addEffect(warrior.getEventFactory());
		}
	}

	public HeraldofAnafenza(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Outlast (2)(W) ((2)(W), (T): Put a +1/+1 counter on this creature.
		// Outlast only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(2)(W)"));

		// Whenever you activate Herald of Anafenza's outlast ability, put a 1/1
		// white Warrior creature token onto the battlefield.
		this.addAbility(new HeraldofAnafenzaAbility1(state));
	}
}
