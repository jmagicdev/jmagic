package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvan Primordial")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("5GG")
@ColorIdentity({Color.GREEN})
public final class SylvanPrimordial extends Card
{
	public static final class SylvanTarget extends Target
	{
		public SylvanTarget(SetGenerator filter, String name)
		{
			super(filter, name);
			this.setNumber(0, null);
		}

		@Override
		public boolean checkSpecialRestrictions(GameState state, java.util.List<Target> choices)
		{
			if(choices.isEmpty())
				return true;

			java.util.List<Integer> controllers = new java.util.LinkedList<Integer>();
			for(Target choice: choices)
			{
				int controller = state.<GameObject>get(choice.targetID).controllerID;
				if(controllers.contains(controller))
					return false;
				controllers.add(controller);
			}

			return true;
		}
	}

	public static final class SylvanPrimordialAbility1 extends EventTriggeredAbility
	{
		public SylvanPrimordialAbility1(GameState state)
		{
			super(state, "When Sylvan Primordial enters the battlefield, for each opponent, destroy target noncreature permanent that player controls. For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator noncreaturePermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.CREATURE));
			SetGenerator legalTargets = Intersect.instance(noncreaturePermanents, ControlledBy.instance(OpponentsOf.instance(You.instance())));
			Target t = new SylvanTarget(legalTargets, "target noncreature permanent each oppenent controls");
			this.addTarget(t);

			EventFactory destroy = destroy(targetedBy(t), "For each opponent, destroy target noncreature permanent that player controls.");
			this.addEffect(destroy);

			SetGenerator X = Count.instance(NewObjectOf.instance(EffectResult.instance(destroy)));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, X);
			search.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.FOREST)));
			this.addEffect(search);
		}
	}

	public SylvanPrimordial(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(8);

		// Reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// When Sylvan Primordial enters the battlefield, for each opponent,
		// destroy target noncreature permanent that player controls. For each
		// permanent destroyed this way, search your library for a Forest card
		// and put that card onto the battlefield tapped. Then shuffle your
		// library.
		this.addAbility(new SylvanPrimordialAbility1(state));
	}
}
