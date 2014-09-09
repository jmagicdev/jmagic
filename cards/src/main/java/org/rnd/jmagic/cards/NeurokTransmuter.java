package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.ContinuousEffectType.Parameter;
import org.rnd.jmagic.engine.generators.*;

@Name("Neurok Transmuter")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class NeurokTransmuter extends Card
{
	// {U}: Target creature becomes an artifact in addition to its other types
	// until end of turn.
	public static final class MakeArtifact extends ActivatedAbility
	{
		public MakeArtifact(GameState state)
		{
			super(state, "(U): Target creature becomes an artifact in addition to its other types until end of turn.");
			this.setManaCost(new ManaPool("U"));
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(Parameter.OBJECT, targetedBy(target));
			part.parameters.put(Parameter.TYPE, Identity.instance(Type.ARTIFACT));

			this.addEffect(createFloatingEffect("Target creature becomes an artifact in addition to its other types until end of turn.", part));
		}
	}

	// {U}: Until end of turn, target artifact creature becomes blue and isn't
	// an artifact.
	public static final class MakeBlue extends ActivatedAbility
	{
		public MakeBlue(GameState state)
		{
			super(state, "(U): Until end of turn, target artifact creature becomes blue and isn't an artifact.");
			this.setManaCost(new ManaPool("U"));
			SetGenerator artifactCreatureCards = Intersect.instance(HasType.instance(Type.ARTIFACT), HasType.instance(Type.CREATURE));
			Target target = this.addTarget(Intersect.instance(Permanents.instance(), artifactCreatureCards), "target artifact creature");

			ContinuousEffect.Part isBlue = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
			isBlue.parameters.put(Parameter.OBJECT, targetedBy(target));
			isBlue.parameters.put(Parameter.COLOR, Identity.instance(Color.BLUE));

			ContinuousEffect.Part notAnArtifact = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			notAnArtifact.parameters.put(Parameter.OBJECT, targetedBy(target));
			notAnArtifact.parameters.put(Parameter.TYPE, Identity.instance(Type.ARTIFACT));

			this.addEffect(createFloatingEffect("Until end of turn, target artifact creature becomes blue and isn't an artifact.", isBlue, notAnArtifact));
		}
	}

	public NeurokTransmuter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new MakeArtifact(state));
		this.addAbility(new MakeBlue(state));
	}
}
