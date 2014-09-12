package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.DamageAssignment.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/**
 * <p>
 * This class creates a static ability, represented by an instance of
 * {@link ProtectionStatic}. Most instances of Protection will use the default
 * implementation, which is {@link ProtectionStatic.Final}. Using that
 * implementation is trivial; you don't need to do anything special. If,
 * however, you need to use a different implementation of ProtectionStatic,
 * define {@link Protection#getProtectionStatic()} to return an instance of your
 * new ProtectionStatic.
 * </p>
 * <p>
 * You will need to make a new instance of ProtectionStatic if one of the
 * parameters you pass to Protection's constructor involves Java's "this"
 * keyword in any way. If you do, that ProtectionStatic's constructor should not
 * take any parameters involving "this".
 * </p>
 */
public abstract class Protection extends Keyword
{
	public static final class AbilityFactory implements org.rnd.jmagic.engine.AbilityFactory
	{
		private static String separate(Color... colors)
		{
			if(colors.length == 1)
				return colors[0].toString();
			if(colors.length == 2)
				// see Great Sable Stag
				return colors[0].toString() + " and from " + colors[1].toString();
			// see Oversoul of Dusk
			return org.rnd.util.SeparatedList.get(", and from ", "", (Object[])colors).toString();
		}

		private SetPattern quality;
		private SetGenerator qualityName;

		public AbilityFactory(Color... colors)
		{
			this(HasColor.instance(colors), separate(colors));
		}

		public AbilityFactory(SetGenerator quality, String qualityName)
		{
			this(new SimpleSetPattern(quality), Identity.instance(qualityName));
		}

		public AbilityFactory(SetGenerator quality, SetGenerator qualityName)
		{
			this(new SimpleSetPattern(quality), qualityName);
		}

		public AbilityFactory(SetPattern quality, SetGenerator qualityName)
		{
			this.quality = quality;
			this.qualityName = qualityName;
		}

		@Override
		public Identified create(GameState state, Identified thisObject)
		{
			String qualityName = this.qualityName.evaluate(state, thisObject).getOne(String.class);
			return new Protection.From(state, this.quality, qualityName);
		}

		@Override
		public Class<?> clazz()
		{
			return Protection.From.class;
		}

	}

	/**
	 * Evaluates to Class objects representing protection from each of the given
	 * colors
	 */
	public static class FromColor extends SetGenerator
	{
		public static SetGenerator instance(SetGenerator colors)
		{
			return new FromColor(colors);
		}

		private SetGenerator colors;

		private FromColor(SetGenerator colors)
		{
			this.colors = colors;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			for(Color c: this.colors.evaluate(state, thisObject).getAll(Color.class))
				ret.add(new Protection.AbilityFactory(c));
			return ret;
		}
	}

	public static Class<? extends Protection> from(Color c)
	{
		switch(c)
		{
		case WHITE:
			return org.rnd.jmagic.abilities.keywords.Protection.FromWhite.class;
		case BLUE:
			return org.rnd.jmagic.abilities.keywords.Protection.FromBlue.class;
		case BLACK:
			return org.rnd.jmagic.abilities.keywords.Protection.FromBlack.class;
		case RED:
			return org.rnd.jmagic.abilities.keywords.Protection.FromRed.class;
		case GREEN:
			return org.rnd.jmagic.abilities.keywords.Protection.FromGreen.class;
		}
		throw new RuntimeException("This can't happen (blame RulesGuru if it does)");
	}

	public static final class From extends Protection
	{
		public From(GameState state, SetGenerator quality, String qualityName)
		{
			super(state, quality, qualityName);
		}

		public From(GameState state, SetPattern quality, String qualityName)
		{
			super(state, quality, qualityName);
		}

		@Override
		public From create(Game game)
		{
			return new From(game.physicalState, this.quality, this.qualityName);
		}
	}

	public static final class FromWhite extends Protection
	{
		public FromWhite(GameState state)
		{
			super(state, Color.WHITE);
		}
	}

	public static final class FromBlue extends Protection
	{
		public FromBlue(GameState state)
		{
			super(state, Color.BLUE);
		}
	}

	public static final class FromBlack extends Protection
	{
		public FromBlack(GameState state)
		{
			super(state, Color.BLACK);
		}
	}

	public static final class FromRed extends Protection
	{
		public FromRed(GameState state)
		{
			super(state, Color.RED);
		}
	}

	public static final class FromGreen extends Protection
	{
		public FromGreen(GameState state)
		{
			super(state, Color.GREEN);
		}
	}

	public static final class FromArtifacts extends Protection
	{
		public FromArtifacts(GameState state)
		{
			super(state, HasType.instance(Type.ARTIFACT), "artifacts");
		}
	}

	public static final class FromCreatures extends Protection
	{
		public FromCreatures(GameState state)
		{
			super(state, HasType.instance(Type.CREATURE), "creatures");
		}
	}

	public static abstract class ProtectionStatic extends StaticAbility
	{
		private SetPattern quality;
		public String qualityName;

		public class ProtectionReplacement extends DamageReplacementEffect
		{
			public ProtectionReplacement(Game game)
			{
				super(game, ProtectionStatic.this.getName());
				this.makePreventionEffect();
			}

			@Override
			public Batch match(Event context, Batch damageAssignments)
			{
				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				Identified thisObject = context.game.actualState.get(ProtectionStatic.this.sourceID);
				for(DamageAssignment damage: damageAssignments)
					if(damage.takerID == thisObject.ID && ProtectionStatic.this.getQuality().match(context.game.actualState, thisObject, new Set(context.state.get(damage.sourceID))))
						ret.add(damage);
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(Batch damageAssignments)
			{
				damageAssignments.clear();
				return new java.util.LinkedList<EventFactory>();
			}
		}

		/**
		 * Protection from something means you can't be targeted by abilities
		 * whose source is that thing. This pattern handles matching abilities'
		 * sources.
		 */
		public static final class ProtectionFromPattern implements SetPattern
		{
			private final SetPattern fromWhat;

			public ProtectionFromPattern(SetPattern fromWhat)
			{
				this.fromWhat = fromWhat;
			}

			@Override
			public boolean match(GameState state, Identified thisObject, Set set)
			{
				for(NonStaticAbility a: set.getAll(NonStaticAbility.class))
					set.add(a.getSource(state));
				return this.fromWhat.match(state, thisObject, set);
			}

			@Override
			public void freeze(GameState state, Identified thisObject)
			{
				// Nothing to freeze
			}
		}

		public ProtectionStatic(GameState state, SetPattern quality, SetPattern doesntRemove, String qualityName)
		{
			super(state, "This can't be damaged, enchanted, equipped, fortified, blocked, or targetted by [" + qualityName + "]");
			this.setQuality(quality, doesntRemove);
			this.qualityName = qualityName;
		}

		public SetPattern getQuality()
		{
			return this.quality;
		}

		public void setQuality(SetPattern quality, SetPattern doesntRemove)
		{
			this.quality = quality;
			SetPattern protectionPattern = new ProtectionFromPattern(quality);

			this.getEffect().parts.clear();

			// D - Damage
			{
				DamageReplacementEffect replacement = new ProtectionReplacement(this.game);

				this.addEffectPart(replacementEffectPart(replacement));
			}

			// E - Enchant, Equip, Fortify
			{
				SetPattern qualityWithExceptions = new RelativeComplementPattern(quality, doesntRemove);
				SetPattern protectionAttachPattern = new ProtectionFromPattern(qualityWithExceptions);

				ContinuousEffect.Part attachRestrictionPart = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_ATTACHED_BY);
				attachRestrictionPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
				attachRestrictionPart.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(protectionAttachPattern));
				this.addEffectPart(attachRestrictionPart);
			}

			// B - Block
			{
				SetGenerator illegalBlock = EvaluatePattern.instance(quality, Blocking.instance(This.instance()));

				ContinuousEffect.Part blockingPart = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
				blockingPart.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlock));
				this.addEffectPart(blockingPart);
			}

			// T - Target
			{
				ContinuousEffect.Part targetRestrictionPart = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
				targetRestrictionPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
				targetRestrictionPart.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(protectionPattern));
				this.addEffectPart(targetRestrictionPart);
			}
		}

		public static final class Final extends ProtectionStatic
		{
			private SetPattern doesntRemove;

			public Final(GameState state, SetPattern quality, SetPattern doesntRemove, String qualityName)
			{
				super(state, quality, doesntRemove, qualityName);
				this.doesntRemove = doesntRemove;
			}

			@Override
			public Final create(Game game)
			{
				return new Final(game.physicalState, this.getQuality(), this.doesntRemove, this.qualityName);
			}
		}
	}

	public SetPattern quality;
	public final String qualityName;

	protected Protection(GameState state, Color color)
	{
		this(state, HasColor.instance(color), color.toString());
	}

	/**
	 * To be called by cards that need to override ProtectionStatic and specify
	 * the quality there.
	 */
	protected Protection(GameState state, String qualityName)
	{
		this(state, (SetPattern)null, qualityName);
	}

	public Protection(GameState state, SetGenerator quality, String qualityName)
	{
		this(state, new SimpleSetPattern(quality), qualityName);
	}

	public Protection(GameState state, SetPattern quality, String qualityName)
	{
		// TODO : consider removing 'from' from the name below. it sounds weird
		// when baneslayer's ability has to send 'demons and from dragons'.
		super(state, "Protection from " + qualityName);

		this.quality = quality;
		this.qualityName = qualityName;
	}

	@Override
	protected final java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(getProtectionStatic());
	}

	protected ProtectionStatic getProtectionStatic()
	{
		return new ProtectionStatic.Final(this.state, this.quality, SetPattern.NEVER_MATCH, this.qualityName);
	}
}
