package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.ManaSymbol.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grand Architect")
@Types({Type.CREATURE})
@SubTypes({SubType.VEDALKEN, SubType.ARTIFICER})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class GrandArchitect extends Card
{
	public static final class GrandArchitectAbility0 extends StaticAbility
	{
		public GrandArchitectAbility0(GameState state)
		{
			super(state, "Other blue creatures you control get +1/+1.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasColor.instance(Color.BLUE)), This.instance()), +1, +1));
		}
	}

	public static final class GrandArchitectAbility1 extends ActivatedAbility
	{
		public GrandArchitectAbility1(GameState state)
		{
			super(state, "(U): Target artifact creature becomes blue until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			part.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLUE));
			this.addEffect(createFloatingEffect("Target artifact creature becomes blue until end of turn.", part));
		}
	}

	public static final class GrandArchitectAbility2 extends ActivatedAbility
	{
		public GrandArchitectAbility2(GameState state)
		{
			super(state, "Tap an untapped blue creature you control: Add (2) to your mana pool. Spend this mana only to cast artifact spells or activate abilities of artifacts.");

			SetGenerator yourBlueCreatures = Intersect.instance(CREATURES_YOU_CONTROL, HasColor.instance(Color.BLUE));
			SetGenerator yourUntappedBlueCreatures = Intersect.instance(Untapped.instance(), yourBlueCreatures);

			EventFactory tapBlueCreature = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped blue creature you control");
			tapBlueCreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapBlueCreature.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapBlueCreature.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			tapBlueCreature.parameters.put(EventType.Parameter.CHOICE, yourUntappedBlueCreatures);
			this.addCost(tapBlueCreature);

			EventFactory factory = new EventFactory(ADD_RESTRICTED_MANA, "Add (2) to your mana pool. Spend this mana only to cast artifact spells or activate abilities of artifacts.");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.ARTIFACT));
			factory.parameters.put(EventType.Parameter.MANA, Identity.instance(ManaType.COLORLESS));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addEffect(factory);
		}
	}

	public GrandArchitect(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Other blue creatures you control get +1/+1.
		this.addAbility(new GrandArchitectAbility0(state));

		// (U): Target artifact creature becomes blue until end of turn.
		this.addAbility(new GrandArchitectAbility1(state));

		// Tap an untapped blue creature you control: Add (2) to your mana pool.
		// Spend this mana only to cast artifact spells or activate abilities of
		// artifacts.
		this.addAbility(new GrandArchitectAbility2(state));
	}
}
